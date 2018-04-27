package com.hp.augmentedprint.data;

import com.hp.augmentedprint.mapschema.MapInformation;
import com.hp.augmentedprint.schema.QrDetail;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by sarath on 14/3/18.
 */

public interface NetworkInterface {
    @GET("/api/v1/print_files/{id}")
    Observable<QrDetail> getQrCodeResult(@Path("id") String apiKey);

}
