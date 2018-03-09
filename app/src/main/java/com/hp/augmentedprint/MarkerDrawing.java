package com.hp.augmentedprint;

import android.graphics.Canvas;

import com.hp.augmentedprint.schema.MarkerInfo;

import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */

public interface MarkerDrawing {

    void placeMarkers(List<MarkerInfo> markerInfo);

    void onZoom(Canvas canvas, float newWidth, float newHeight);

    void onZoomFinished();

    void setMarkerClickListener(MarkerClickListener markerClickListener);

    void destroy();

    void hideMarkers();

    void showMarkers();


    void clearMarkers();
}
