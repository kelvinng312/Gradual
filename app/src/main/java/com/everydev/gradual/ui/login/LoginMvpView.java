package com.everydev.gradual.ui.login;

import com.everydev.gradual.data.network.pojo.UserProfile;
import com.everydev.gradual.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    void onLoginSuccess(UserProfile mUser);

    String getEmail();

    String getPassword();

    void showInputError();

    void setPassword(String userId);

    void setEmail(String password);
}
