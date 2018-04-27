package com.hp.augmentedprint.mapschema;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hp.augmentedprint.schema.MapPage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sarath on 14/3/18.
 */

public class MapInformation implements Parcelable {
    @SerializedName("map_info")
    public List<LinkedHashMap<String, MapPage>> mapInfo;
    @SerializedName("url")
    public String url;

    public String filename;

    protected MapInformation(Parcel in) {
        url = in.readString();
        filename = in.readString();
    }

    public static final Creator<MapInformation> CREATOR = new Creator<MapInformation>() {
        @Override
        public MapInformation createFromParcel(Parcel in) {
            return new MapInformation(in);
        }

        @Override
        public MapInformation[] newArray(int size) {
            return new MapInformation[size];
        }
    };

    @Override
    public String toString() {
        return "MapInformation{" +
                "mapInfo=" + mapInfo +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(filename);
    }
}
