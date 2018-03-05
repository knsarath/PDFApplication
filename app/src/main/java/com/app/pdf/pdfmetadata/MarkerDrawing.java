package com.app.pdf.pdfmetadata;

import android.graphics.Canvas;

import com.app.pdf.pdfmetadata.schema.MarkerInfo;

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
