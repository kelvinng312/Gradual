package com.everydev.gradual.di.component;


import com.everydev.gradual.di.PerActivity;
import com.everydev.gradual.di.module.ActivityModule;
import com.everydev.gradual.ui.donees.DoneesActivity;
import com.everydev.gradual.ui.login.LoginActivity;
import com.everydev.gradual.ui.main.MainActivity;
import com.everydev.gradual.ui.signup.SignupActivity;

import dagger.Component;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(SignupActivity signupActivity);

    void inject(MainActivity mainActivity);

    void inject(DoneesActivity doneesActivity);
}