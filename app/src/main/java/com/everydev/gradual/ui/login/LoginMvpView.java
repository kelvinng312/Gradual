package com.everydev.gradual.ui.login;

import com.everydev.gradual.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    void onLoginSuccess(Long userId);
    String getEmail();
    String getPassword();
}
