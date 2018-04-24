package com.hp.augmentedprint.schema;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerData {
    @SerializedName("data")
    public String data;
    @SerializedName("redirect_uri")
    public String redirectUrl;

    public MarkerData(String data) {
        this.data = data;
    }
}
