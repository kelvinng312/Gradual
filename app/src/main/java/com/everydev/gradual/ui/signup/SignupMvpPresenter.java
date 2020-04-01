package com.everydev.gradual.ui.signup;

import com.everydev.gradual.ui.base.MvpPresenter;

public interface SignupMvpPresenter<V extends SignupMvpView> extends MvpPresenter<V> {
    void onSignupClick();
}
