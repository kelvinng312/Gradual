package com.everydev.gradual.ui.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.everydev.gradual.R;
import com.everydev.gradual.ui.base.BaseActivity;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    private LottieAnimationView mLavDonate;
    private LottieAnimationView mLabSuccess;

    private MediaPlayer mPlayerDonate;
    private MediaPlayer mPlayerSuccess;

    private LinearLayout mLayoutCard;
    private CardInputWidget mCardInputWidget;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);
        setUp();

    }

    @Override
    protected void setUp() {
        // Donate animation
        mLavDonate = findViewById(R.id.lav_donate);
        mLavDonate.setProgress(1f);
        mLavDonate.setOnClickListener(v -> {
            mLayoutCard.setVisibility(View.INVISIBLE);

            mPlayerDonate.seekTo(0);
            mPlayerDonate.start();

            // Play animation
            mLavDonate.setProgress(0);
            mLavDonate.pauseAnimation();
            mLavDonate.playAnimation();

            // donate
            PaymentMethodCreateParams params = mCardInputWidget.getPaymentMethodCreateParams();
            mPresenter.pay(params);
        });

        // Success animation
        mLabSuccess = findViewById(R.id.lav_success);
        mLabSuccess.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLabSuccess.setProgress(0);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // Donate sound
        mPlayerDonate = MediaPlayer.create(this, R.raw.button_sound);
        mPlayerDonate.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mLavDonate.setProgress(1f);
                mLavDonate.setVisibility(View.INVISIBLE);

                mPlayerSuccess.seekTo(0);
                mPlayerSuccess.start();

                // Play success animation
                mLabSuccess.setProgress(0);
                mLabSuccess.setRepeatCount(ValueAnimator.INFINITE);
                mLabSuccess.playAnimation();
            }
        });

        mPlayerSuccess = MediaPlayer.create(this, R.raw.success_sound);
        mPlayerSuccess.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mLabSuccess.setProgress(0);
                mLabSuccess.pauseAnimation();

                mLavDonate.setVisibility(View.VISIBLE);
            }
        });

        // layout
        mLayoutCard = findViewById(R.id.layout_card);
        mCardInputWidget = findViewById(R.id.card_input_widget);

        // prepare
        mPresenter.onViewPrepared();
    }

    @Override
    protected void onDestroy() {
        mPlayerDonate.release();
        mPlayerSuccess.release();

        super.onDestroy();
    }

    private void pay() {

    }

    @Override
    public void showCardInputWidget(boolean show) {
        if (show)
            mLayoutCard.setVisibility(View.VISIBLE);
        else
            mLayoutCard.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPaymentSuccess() {
        showMessage("Donation success!");
    }

    @Override
    public void handleNextActionForPayment(Stripe stripe, String paymentIntentClientSecret) {
        runOnUiThread(() -> {
            stripe.handleNextActionForPayment(MainActivity.this, paymentIntentClientSecret);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        mPresenter.onPaymentResult(requestCode, data);
    }
}
