package com.hp.augmentedprint.schema;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerData implements Parcelable{
    @SerializedName("data")
    public String data;
    @SerializedName("redirect_uri")
    public String redirectUrl;

    public MarkerData(String data) {
        this.data = data;
    }

    protected MarkerData(Parcel in) {
        data = in.readString();
        redirectUrl = in.readString();
    }

    public static final Creator<MarkerData> CREATOR = new Creator<MarkerData>() {
        @Override
        public MarkerData createFromParcel(Parcel in) {
            return new MarkerData(in);
        }

        @Override
        public MarkerData[] newArray(int size) {
            return new MarkerData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeString(redirectUrl);
    }
}
