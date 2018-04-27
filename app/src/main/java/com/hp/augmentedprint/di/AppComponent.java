package com.hp.augmentedprint.di;

import android.app.Activity;

import com.hp.augmentedprint.App;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by sarath on 19/3/18.
 */
@ApplicationScope
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class
})
public interface AppComponent {
    void inject(App app);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(App app);
        AppComponent build();
    }
}
