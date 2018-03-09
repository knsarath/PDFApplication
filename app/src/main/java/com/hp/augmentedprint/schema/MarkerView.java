package com.hp.augmentedprint.schema;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerView extends android.support.v7.widget.AppCompatTextView {

    MarkerInfo mMarkerInfo;

    public MarkerView(Context context) {
        super(context);
    }

    public MarkerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setMarkerInfo(MarkerInfo markerInfo) {
        mMarkerInfo = markerInfo;
    }

    public MarkerInfo getMarkerInfo() {
        return mMarkerInfo;
    }
}
