package com.hp.augmentedprint.ui;

import com.hp.augmentedprint.data.NetworkInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sarath on 14/3/18.
 */

public class Injector {
    private static NetworkInterface sNetworkInterface;
    public static NetworkInterface getNetworkInterface() {
        if (sNetworkInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://35.170.94.217/")
                    .client(okhttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sNetworkInterface = retrofit.create(NetworkInterface.class);
        }
        return sNetworkInterface;
    }

    private static OkHttpClient okhttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    }
}
