package com.everydev.gradual.di.module;

import android.content.Context;

import com.everydev.gradual.di.ActivityContext;
import com.everydev.gradual.di.PerActivity;
import com.everydev.gradual.ui.donees.DoneesMvpPresenter;
import com.everydev.gradual.ui.donees.DoneesMvpView;
import com.everydev.gradual.ui.donees.DoneesPresenter;
import com.everydev.gradual.ui.login.LoginMvpPresenter;
import com.everydev.gradual.ui.login.LoginMvpView;
import com.everydev.gradual.ui.login.LoginPresenter;
import com.everydev.gradual.ui.main.MainMvpPresenter;
import com.everydev.gradual.ui.main.MainMvpView;
import com.everydev.gradual.ui.main.MainPresenter;
import com.everydev.gradual.ui.main.RssAdapter;
import com.everydev.gradual.ui.signup.SignupMvpPresenter;
import com.everydev.gradual.ui.signup.SignupMvpView;
import com.everydev.gradual.ui.signup.SignupPresenter;
import com.everydev.gradual.utils.rx.AppSchedulerProvider;
import com.everydev.gradual.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignupMvpPresenter<SignupMvpView> provideSignupPresenter(SignupPresenter<SignupMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DoneesMvpPresenter<DoneesMvpView> provideDoneesPresenter(DoneesPresenter<DoneesMvpView> presenter) {
        return presenter;
    }


    @Provides
    RssAdapter provideRssAdapter() {
        return new RssAdapter(new ArrayList<>());
    }
}