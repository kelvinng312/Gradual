package com.everydev.gradual.ui.main;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.everydev.gradual.data.DataManager;
import com.everydev.gradual.data.network.pojo.PayAgainRequest;
import com.everydev.gradual.data.network.pojo.PayConfirmRequest;
import com.everydev.gradual.data.network.pojo.PayFirstRequest;
import com.everydev.gradual.data.network.pojo.PayResponse;
import com.everydev.gradual.ui.base.BasePresenter;
import com.everydev.gradual.utils.rx.SchedulerProvider;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private Application mApplicationContext;
    private Stripe mStripe;

    @Inject
    public MainPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable,
            Application applicationContext) {
        super(manager, schedulerProvider, compositeDisposable);
        mApplicationContext = applicationContext;
    }

    @Override
    public void onViewPrepared() {
        String customerID = getCustomerId();
        if (customerID.isEmpty())
            getMvpView().showCardInputWidget(true);
        else
            getMvpView().showCardInputWidget(false);

        String stripeKey = getDataManager().getStripeKey().trim();
        mStripe = new Stripe(mApplicationContext, stripeKey);
    }

    @Override
    public void pay(PaymentMethodCreateParams params) {
        // show loading
        getMvpView().showLoading();

        // check if there is a customer ID
        String customerID = getDataManager().getCustomerID().trim();
        if (!customerID.isEmpty()) {
            payWithCustomerID(customerID);
        } else {
            // pay with card
            mStripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
                @Override
                public void onSuccess(PaymentMethod paymentMethod) {
                    payWithPaymentMethodID(paymentMethod.id);
                }

                @Override
                public void onError(@NotNull Exception e) {
                    // hide loading
                    getMvpView().hideLoading();
                }
            });
        }
    }

    @Override
    public void onPaymentResult(int requestCode, Intent data) {
        mStripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    @Override
    public String getCustomerId() {
        return getDataManager().getCustomerID().trim();
    }

    private void payWithPaymentMethodID(String paymentMethodID) {
        // check view
        if (!isViewAttached()) {
            // hide loading
            getMvpView().hideLoading();
            return;
        }

        // api call
        PayFirstRequest payFirstRequest = new PayFirstRequest();
        payFirstRequest.setUserId(getDataManager().getUserId());
        payFirstRequest.setPaymentMethodId(paymentMethodID);

        getCompositeDisposable().add(getDataManager()
                .payFirst(payFirstRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    processPayResponse(response);
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }

    private void payWithCustomerID(String customerID) {
        // check view
        if (!isViewAttached()) {
            return;
        }

        // show loading
        getMvpView().showLoading();

        // api call
        PayAgainRequest payAgainRequest = new PayAgainRequest();
        payAgainRequest.setUserId(getDataManager().getUserId());
        payAgainRequest.setCustomerId(customerID);

        getCompositeDisposable().add(getDataManager()
                .payAgain(payAgainRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    processPayResponse(response);
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }

    private void confirmPayment(String paymentIntentID) {
        // check view
        if (!isViewAttached()) {
            return;
        }

        // show loading
        getMvpView().showLoading();

        // api call
        PayConfirmRequest payConfirmRequest = new PayConfirmRequest();
        payConfirmRequest.setUserId(getDataManager().getUserId());
        payConfirmRequest.setPaymentIntentId(paymentIntentID);

        getCompositeDisposable().add(getDataManager()
                .payConfirm(payConfirmRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    processPayResponse(response);
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }

    private void onPaymentSuccess() {
        getMvpView().onPaymentSuccess();
    }

    private void onPaymentIncompleted(String description) {
        getMvpView().onPaymentIncompleted(description);
    }

    // utility function
    private void processPayResponse(PayResponse payResponse) {
        if (payResponse.getStatus().contentEquals("error")) {
            getMvpView().onPaymentIncompleted(payResponse.getError());
        } else if (payResponse.getStatus().contentEquals("accumulated")) {
            getMvpView().onPaymentIncompleted(payResponse.getError());
        } else if (payResponse.getStatus().contentEquals("success")) {
            // save customer id
            if (!payResponse.getCustomerId().isEmpty()) {
                getDataManager().setCustomerID(payResponse.getCustomerId());
            }

            // process
            if (!payResponse.getClientSecret().isEmpty()) {
                if (payResponse.isRequiresAction()) {
                    getMvpView().handleNextActionForPayment(mStripe, payResponse.getClientSecret());
                } else {
                    getMvpView().onPaymentSuccess();
                }
            }
        }
    }

    // utility class
    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        private final WeakReference<MainPresenter> mMainPresenterWeakReference;

        PaymentResultCallback(@NonNull MainPresenter mainPresenter) {
            mMainPresenterWeakReference = new WeakReference<>(mainPresenter);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final MainPresenter mainPresenter = mMainPresenterWeakReference.get();
            if (mainPresenter == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                mainPresenter.onPaymentSuccess();

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                mainPresenter.onPaymentIncompleted("Donation failed");

            } else if (status == PaymentIntent.Status.RequiresConfirmation) {
                // After handling a required action on the client, the status of the PaymentIntent is
                // requires_confirmation. You must send the PaymentIntent ID to your backend
                // and confirm it to finalize the payment. This step enables your integration to
                // synchronously fulfill the order on your backend and return the fulfillment result
                // to your client.
                mainPresenter.confirmPayment(paymentIntent.getId());
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final MainPresenter mainPresenter = mMainPresenterWeakReference.get();
            if (mainPresenter == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            mainPresenter.onPaymentIncompleted(e.toString());
        }
    }
}
