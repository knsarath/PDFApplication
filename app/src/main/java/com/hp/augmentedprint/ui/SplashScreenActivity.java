package com.hp.augmentedprint.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hp.augmentedprint.common.BaseActivity;
import com.hp.augmentedprint.pdfmetadata.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableCompletableObserver;


public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        addToDisposable(Completable.complete()
                .delay(3, TimeUnit.SECONDS)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        launchMainScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        launchMainScreen();
                    }
                }));

    }

    private void launchMainScreen() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
