package com.hp.augmentedprint;

import android.app.Activity;
import android.app.Application;

import com.hp.augmentedprint.common.broadcast.RxBus;
import com.hp.augmentedprint.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by sarath on 9/3/18.
 */

public class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    public static RxBus mRxBus = new RxBus();

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }
}
