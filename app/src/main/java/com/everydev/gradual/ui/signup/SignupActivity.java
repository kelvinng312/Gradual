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
import com.everydev.gradual.ui.main.MainActivity;

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
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPassword = findViewById(R.id.edt_password);
        mEdtConfirmPassword = findViewById(R.id.edt_confirm_password);
        mEdtName = findViewById(R.id.edt_name);

        mBtnSignup = findViewById(R.id.btn_signup);
        mBtnSignup.setOnClickListener(v -> {
            if (validate())
                mPresenter.onSignupClick();
        });

        mTxtLogin = findViewById(R.id.txt_login);
        mTxtLogin.setOnClickListener(v -> {
            startActivity(LoginActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });
    }

    @Override
    public void onSignupSuccess() {
        startActivity(MainActivity.getStartIntent(this));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public String getEmail() {
        return mEdtEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }

    @Override
    public String getConfirmPassword() {
        return mEdtConfirmPassword.getText().toString().trim();
    }

    @Override
    public String getName() {
        return mEdtName.getText().toString().trim();
    }

    private boolean validate() {
        if (getEmail().isEmpty()) {
            showMessage(getString(R.string.email_invalid));
            return false;
        }

        if (getPassword().isEmpty()) {
            showMessage(getString(R.string.password_invalid));
            return false;
        }

        if (getPassword().length() < 6) {
            showMessage(getString(R.string.password_length));
            return false;
        }

        if (!getPassword().contentEquals(getConfirmPassword())) {
            showMessage(getString(R.string.confirm_invalid));
            return false;
        }

        return true;
    }
}
