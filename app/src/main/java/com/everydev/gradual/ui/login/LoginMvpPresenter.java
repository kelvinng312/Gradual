package com.everydev.gradual.ui.login;

import com.everydev.gradual.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onLoginClick();
}
