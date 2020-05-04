package com.everydev.gradual.ui.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
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

    public static Intent getStartIntent(Context context, String name, String avatar, String pubkey) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("AVATAR", avatar);
        intent.putExtra("PUBKEY", pubkey);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);
        setUp();

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getExtras().getString("NAME");
            String avatar = intent.getExtras().getString("AVATAR");
            String pubkey = intent.getExtras().getString("PUBKEY");
            ImageView sendImageView = findViewById(R.id.sendImageView);
            Glide.with(sendImageView).load(avatar).into(sendImageView);

            TextView tbSend = findViewById(R.id.tbSendName);
            tbSend.setText(name);
        }
    }

    @Override
    protected void setUp() {
        // Donate animation
        mLavDonate = findViewById(R.id.lav_donate);
        mLavDonate.setProgress(1f);
        mLavDonate.setOnClickListener(v -> {
            // validate
            if (mPresenter.getCustomerId().isEmpty()) {
                PaymentMethodCreateParams params = mCardInputWidget.getPaymentMethodCreateParams();
                if (params == null) {
                    showMessage("Please input Card information correctly");
                    return;
                }
            }

            // start sound
            mLayoutCard.setVisibility(View.INVISIBLE);

            mPlayerDonate.seekTo(0);
            mPlayerDonate.start();

            // Play animation
            mLavDonate.setProgress(0);
            mLavDonate.pauseAnimation();
            mLavDonate.playAnimation();
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

                // donate
                PaymentMethodCreateParams params = mCardInputWidget.getPaymentMethodCreateParams();
                mPresenter.pay(params);
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

    @Override
    public void showCardInputWidget(boolean show) {
        if (show)
            mLayoutCard.setVisibility(View.VISIBLE);
        else
            mLayoutCard.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPaymentSuccess() {
        // Play success sound
        mPlayerSuccess.seekTo(0);
        mPlayerSuccess.start();

        // Play success animation
        mLabSuccess.setProgress(0);
        mLabSuccess.setRepeatCount(ValueAnimator.INFINITE);
        mLabSuccess.playAnimation();
    }

    @Override
    public void onPaymentIncompleted(String description) {
        mLavDonate.setVisibility(View.VISIBLE);
        showMessage(description);
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
