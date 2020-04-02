package com.everydev.gradual.ui.login;

import android.os.Handler;

import com.everydev.gradual.data.DataManager;
import com.everydev.gradual.data.network.pojo.LoginRequest;
import com.everydev.gradual.data.network.pojo.LoginResponse;
import com.everydev.gradual.data.network.pojo.StripeKeyResponse;
import com.everydev.gradual.data.utils.LoggedInMode;
import com.everydev.gradual.ui.base.BasePresenter;
import com.everydev.gradual.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {
    @Inject
    public LoginPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClick() {
        // check view
        if (!isViewAttached()) {
            return;
        }

        // show loading
        getMvpView().showLoading();

        // api call
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(getMvpView().getEmail());
        loginRequest.setPassword(getMvpView().getPassword());

        getCompositeDisposable().add(getDataManager()
                .login(loginRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    if (!response.getToken().isEmpty()) {
                        getDataManager().setUserId(response.getId());
                        getDataManager().setStripeKey(response.getPublishableKey());

                        getMvpView().onLoginSuccess();
                    }
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }
}
