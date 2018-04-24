package com.hp.augmentedprint.data;

import com.hp.augmentedprint.mapschema.MapInformation;
import com.hp.augmentedprint.schema.QrDetails;

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
public static final String META_DATA_URL = "https://gist.githubusercontent.com/knsarath/40eae118b62047617663f819954fee2b/raw/fdbab890d381cdaa834a141d4b4e76f489d88a44/test";

    @GET
    Observable<MapInformation> mapMetaData(@Url String url);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

    @GET("/api/v1/print_files/{id}")
    Observable<QrDetails> getQrCodeResult(@Path("id") String apiKey);

}
