package com.everydev.gradual.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.everydev.gradual.R;
import com.everydev.gradual.ui.base.BaseActivity;
import com.everydev.gradual.ui.login.LoginActivity;

import javax.inject.Inject;

public class SignupActivity extends BaseActivity implements SignupMvpView {

    @Inject
    SignupMvpPresenter<SignupMvpView> mPresenter;

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private EditText mEdtName;
    private Button mBtnSignup;
    private TextView mTxtLogin;

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
        mEdtEmail = findViewById(R.id.edt_password);
        mEdtPassword = findViewById(R.id.edt_password);
        mEdtConfirmPassword = findViewById(R.id.edt_confirm_password);
        mEdtName = findViewById(R.id.edt_name);

        mBtnSignup = findViewById(R.id.btn_signup);
        mBtnSignup.setOnClickListener(v -> {

        });

        mTxtLogin = findViewById(R.id.txt_login);
        mTxtLogin.setOnClickListener(v -> {
            startActivity(LoginActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    @Override
    public void onSignupSuccess() {

    }
}
