package com.everydev.gradual.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.everydev.gradual.R;
import com.everydev.gradual.data.network.pojo.UserProfile;
import com.everydev.gradual.ui.base.BaseActivity;

import javax.inject.Inject;

public class SignupActivity extends BaseActivity implements SignupMvpView {

    @Inject
    SignupMvpPresenter<SignupMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SignupActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onSignupSuccess(UserProfile mUser) {

    }
}
