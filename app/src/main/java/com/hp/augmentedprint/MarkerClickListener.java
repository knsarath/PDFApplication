package com.hp.augmentedprint;

import com.hp.augmentedprint.schema.MarkerInfo;
import com.hp.augmentedprint.schema.MarkerView;

/**
 * Created by sarath on 5/3/18.
 */

public interface MarkerClickListener {
    void onMarkerClicked(MarkerView markerView, MarkerInfo markerInfo);
}
