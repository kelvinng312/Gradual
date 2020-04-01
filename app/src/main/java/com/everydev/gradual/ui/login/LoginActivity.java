package com.everydev.gradual.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.everydev.gradual.R;
import com.everydev.gradual.ui.base.BaseActivity;
import com.everydev.gradual.ui.main.MainActivity;
import com.everydev.gradual.ui.signup.SignupActivity;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private TextView mTxtSignup;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(LoginActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPassword = findViewById(R.id.edt_password);

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(v -> {
          mPresenter.onLoginClick();
        });

        mTxtSignup = findViewById(R.id.txt_signup);
        mTxtSignup.setOnClickListener(v -> {
            startActivity(SignupActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void onLoginSuccess() {
        startActivity(MainActivity.getStartIntent(this));
    }

    @Override
    public String getEmail() {
        return mEdtEmail.getText().toString();
    }

    @Override
    public String getEdtPassword() {
        return mEdtPassword.getText().toString();
    }

    @Override
    public void showInputError() {
        showMessage(getString(R.string.input_invalid));
    }

    @Override
    public void setEdtPassword(String edtPassword) {
        mEdtEmail.setText(edtPassword);
    }

    @Override
    public void setEmail(String email) {
        mEdtPassword.setText(email);
    }


}

