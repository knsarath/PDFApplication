package com.hp.augmentedprint.schema;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */

public class MapPage {
    @SerializedName("marker_info_list")
    public List<MarkerInfo> mMarkerInfos;

    public MapPage(List<MarkerInfo> markerInfos) {
        mMarkerInfos = markerInfos;
    }
}
