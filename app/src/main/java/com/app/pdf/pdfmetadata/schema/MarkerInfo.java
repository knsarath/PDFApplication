package com.app.pdf.pdfmetadata.schema;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerInfo {
    @SerializedName("marker_position")
    public PointCoordinate pointCoordinate;
    @SerializedName("marker_size")
    public MarkerSize mMarkerSize;
    @SerializedName("marker_metadata")
    public MarkerData mMarkerData;


    public MarkerInfo(PointCoordinate pointCoordinate, MarkerSize markerSize, MarkerData markerData) {
        this.pointCoordinate = pointCoordinate;
        mMarkerSize = markerSize;
        mMarkerData = markerData;
    }
}
