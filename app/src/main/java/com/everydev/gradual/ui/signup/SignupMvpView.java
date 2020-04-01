package com.everydev.gradual.ui.signup;

import com.everydev.gradual.data.network.pojo.UserProfile;
import com.everydev.gradual.ui.base.MvpView;

public interface SignupMvpView extends MvpView {
    void onSignupSuccess(UserProfile mUser);
}
