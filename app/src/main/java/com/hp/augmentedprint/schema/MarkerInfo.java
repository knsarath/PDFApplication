package com.hp.augmentedprint.schema;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerInfo implements Parcelable {
    @SerializedName("marker_position")
    public PointCoordinate pointCoordinate;
    @SerializedName("marker_size")
    public MarkerSize markerSize;
    @SerializedName("marker_metadata")
    public MarkerData markerData;
    @SerializedName("markerType")
    public String markerType;

    protected MarkerInfo(Parcel in) {
        markerType = in.readString();
    }

    public static final Creator<MarkerInfo> CREATOR = new Creator<MarkerInfo>() {
        @Override
        public MarkerInfo createFromParcel(Parcel in) {
            return new MarkerInfo(in);
        }

        @Override
        public MarkerInfo[] newArray(int size) {
            return new MarkerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(markerType);
    }

    @StringDef({MarkerType.INFO, MarkerType.HYPERLINK})
    public @interface MarkerType {
        String INFO = "info";
        String HYPERLINK = "hyperlink";
    }


    public MarkerInfo(PointCoordinate pointCoordinate, MarkerSize markerSize, @MarkerType String markerType, MarkerData markerData) {
        this.pointCoordinate = pointCoordinate;
        this.markerSize = markerSize;
        this.markerType = markerType;
        this.markerData = markerData;
    }
}
