package com.everydev.gradual.ui.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.everydev.gradual.R;
import com.everydev.gradual.data.network.pojo.FeedItem;
import com.everydev.gradual.ui.base.BaseActivity;
import com.everydev.gradual.utils.DividerItemDecoration;
import com.stripe.android.view.CardInputWidget;

import java.util.List;

import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

}
