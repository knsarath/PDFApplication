package com.hp.augmentedprint.mapschema;

import com.google.gson.annotations.SerializedName;
import com.hp.augmentedprint.schema.MapPage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sarath on 14/3/18.
 */

public class MapInformation {
    @SerializedName("map_info")
    public List<HashMap<String, MapPage>> mapInfo;
    @SerializedName("url")
    public String url;

    @Override
    public String toString() {
        return "MapInformation{" +
                "mapInfo=" + mapInfo +
                ", url='" + url + '\'' +
                '}';
    }
}
