package com.hp.augmentedprint.schema;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */

public class MapPage implements Parcelable{
    @SerializedName("marker_info_list")
    public List<MarkerInfo> mMarkerInfos;

    public MapPage(List<MarkerInfo> markerInfos) {
        mMarkerInfos = markerInfos;
    }

    protected MapPage(Parcel in) {
    }

    public static final Creator<MapPage> CREATOR = new Creator<MapPage>() {
        @Override
        public MapPage createFromParcel(Parcel in) {
            return new MapPage(in);
        }

        @Override
        public MapPage[] newArray(int size) {
            return new MapPage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
