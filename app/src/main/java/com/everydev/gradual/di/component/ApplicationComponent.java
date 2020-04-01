package com.everydev.gradual.di.component;

import android.app.Application;
import android.content.Context;

import com.everydev.gradual.data.DataManager;
import com.everydev.gradual.di.ApplicationContext;
import com.everydev.gradual.di.module.ApplicationModule;
import com.everydev.gradual.root.WaveApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(WaveApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
//    @Component.Builder
//    interface Builder {
//        ApplicationComponent build();
//
//        Builder applicationModule(ApplicationModule applicationModule);
//
//        DataManager getDataManager();
//    }


}
