package com.hp.augmentedprint.di;

import com.hp.augmentedprint.common.broadcast.AppBroadCast;
import com.hp.augmentedprint.common.broadcast.RxBus;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {
    @Binds
    @ApplicationScope
    abstract AppBroadCast bindsAppBroadCast(RxBus rxBus);
}
