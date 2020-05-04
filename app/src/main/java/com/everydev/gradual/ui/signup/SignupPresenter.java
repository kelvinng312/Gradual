package com.everydev.gradual.ui.signup;

import com.everydev.gradual.data.DataManager;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.SignupRequest;
import com.everydev.gradual.ui.base.BasePresenter;
import com.everydev.gradual.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SignupPresenter<V extends SignupMvpView> extends BasePresenter<V>
        implements SignupMvpPresenter<V> {

    @Inject
    public SignupPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSignupClick() {
        // check view
        if (!isViewAttached()) {
            return;
        }

        // show loading
        getMvpView().showLoading();

        // api call
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(getMvpView().getEmail());
        signupRequest.setPassword(getMvpView().getPassword());
        signupRequest.setConfirmPassword(getMvpView().getConfirmPassword());
        signupRequest.setName(getMvpView().getName());

        getCompositeDisposable().add(getDataManager()
                .signup(signupRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    if (!response.getToken().isEmpty()) {
                        getDataManager().setUserId(response.getId());
                        getDataManager().setStripeKey(response.getPublishableKey());
                        getDataManager().setCustomerID(response.getCustomerID());

                        getMvpView().onSignupSuccess(response.getId());
                    }
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }
}
