package com.everydev.gradual.ui.signup;

import com.everydev.gradual.data.DataManager;
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

    }
}
