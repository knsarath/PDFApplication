package com.hp.augmentedprint.schema;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerInfo {
    @SerializedName("marker_position")
    public PointCoordinate pointCoordinate;
    @SerializedName("marker_size")
    public MarkerSize markerSize;
    @SerializedName("marker_metadata")
    public MarkerData markerData;
    @SerializedName("markerType")
    public String markerType;

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
