package com.app.pdf.pdfmetadata;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.pdf.pdfmetadata.schema.MarkerInfo;
import com.app.pdf.pdfmetadata.schema.MarkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerDrawer implements MarkerDrawing, View.OnClickListener {
    private static final String TAG = "MarkerDrawer";
    ViewGroup mMarkingCanvas;
    private List<MarkerView> mMarkers;
    private MarkerClickListener mMarkerClickListener;

    public MarkerDrawer(ViewGroup markingCanvas) {
        mMarkingCanvas = markingCanvas;
    }

    @Override
    public void placeMarkers(List<MarkerInfo> markerInfos) {
        if (mMarkers != null) {
            mMarkingCanvas.removeAllViews();
            mMarkers = null;
        }
        for (MarkerInfo markerInfo : markerInfos) {
            placeMarker(markerInfo);
        }
    }

    private void placeMarker(MarkerInfo markerInfo) {
        MarkerView markerView = new MarkerView(mMarkingCanvas.getContext());
        int width = (int) dpToPx(markerInfo.mMarkerSize.width);
        int height = (int) dpToPx(markerInfo.mMarkerSize.height);
        markerView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        markerView.setBackgroundDrawable(ContextCompat.getDrawable(markerView.getContext(), R.drawable.ic_info_outline_black_24dp));
        markerView.setVisibility(View.GONE);
        markerView.setMarkerInfo(markerInfo);
        markerView.setOnClickListener(this);
        if (mMarkers == null) mMarkers = new ArrayList<>();
        mMarkers.add(markerView);
        mMarkingCanvas.addView(markerView);
    }

    @Override
    public void onZoom(Canvas canvas, float newWidth, float newHeight) {
        if (mMarkers != null) {
            Rect clipBounds = canvas.getClipBounds();
            for (MarkerView markerView : mMarkers) {
                MarkerInfo markerInfo = markerView.getMarkerInfo();
                float widthFactor = markerInfo.pointCoordinate.leftPercentage / 100f;
                float heightFactor = markerInfo.pointCoordinate.topPercentage / 100f;
                float pointXinNewCanvas = (newWidth * widthFactor) - clipBounds.left - (dpToPx(markerInfo.mMarkerSize.width) * 0.5f);
                float pointYInNewCanvas = (newHeight * heightFactor) - clipBounds.top - (dpToPx(markerInfo.mMarkerSize.height) * 0.5f);
                markerView.setX(pointXinNewCanvas);
                markerView.setY(pointYInNewCanvas);
                markerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onZoomFinished() {

    }

    @Override
    public void setMarkerClickListener(MarkerClickListener markerClickListener) {
        mMarkerClickListener = markerClickListener;
    }

    @Override
    public void destroy() {
        mMarkingCanvas = null;
        mMarkers = null;
        Log.e(TAG, "destroy: ");
    }

    @Override
    public void hideMarkers() {
        setMarkersVisibility(View.GONE);
    }

    private void setMarkersVisibility(int visibility) {
        if (mMarkers != null)
            for (MarkerView marker : mMarkers) {
                marker.setVisibility(visibility);
            }
    }

    @Override
    public void showMarkers() {
        setMarkersVisibility(View.VISIBLE);
    }

    @Override
    public void clearMarkers() {
        mMarkers = null;
        mMarkingCanvas.removeAllViews();
        mMarkerClickListener = null;
    }


    @Override
    public void onClick(View v) {
        if (v instanceof MarkerView && mMarkerClickListener != null) {
            MarkerView markerView = (MarkerView) v;
            mMarkerClickListener.onMarkerClicked(markerView, markerView.getMarkerInfo());
        }
    }

    public static float pxToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
