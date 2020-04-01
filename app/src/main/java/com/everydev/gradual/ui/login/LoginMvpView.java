package com.everydev.gradual.ui.login;

import com.everydev.gradual.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    boolean validate();

    void onLoginSuccess();

    String getEmail();
    void setEmail(String password);

    String getPassword();
    void setPassword(String userId);
}
