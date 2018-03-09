package com.hp.augmentedprint;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by sarath on 9/3/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
