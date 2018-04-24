package com.hp.augmentedprint.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hp.augmentedprint.MarkerClickListener;
import com.hp.augmentedprint.MarkerDrawer;
import com.hp.augmentedprint.MarkerDrawing;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.pdfmetadata.databinding.MapFragmentBinding;
import com.hp.augmentedprint.schema.MapPage;
import com.hp.augmentedprint.schema.MarkerInfo;
import com.hp.augmentedprint.schema.MarkerView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by sarath on 13/3/18.
 */

public class PDFMapFragment extends Fragment implements MarkerClickListener {

    private MapFragmentBinding mBinding;
    private MarkerDrawing mMarkerDrawing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadPDF();
    }

    private void loadPDF() {
        mMarkerDrawing = new MarkerDrawer(mBinding.pdfView);
        mBinding.pdfView.fromUri(getMap().second)
                .onPageChange((pagenum, pageCount) -> {
                    Log.d(TAG, "onPageChange: " + pagenum);
                    List<MarkerInfo> markerInfoList = getMap().first.mMarkerInfos;
                    mMarkerDrawing.placeMarkers(markerInfoList);
                })
                .onDraw((canvas, pageWidth, pageHeight, displayedPage) -> mMarkerDrawing.onZoom(canvas, pageWidth, pageHeight))
                .load();
        mMarkerDrawing.setMarkerClickListener(this);
    }


    @Override
    public void onMarkerClicked(MarkerView markerView, MarkerInfo markerInfo) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Info")
                .setMessage(markerInfo.markerData.data)
                .setPositiveButton("OK", this::dismiss)
                .setNegativeButton("CANCEL", this::dismiss)
                .show();
    }

    private void dismiss(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.unbind();
    }

    public static PDFMapFragment createInstance(MapPage mapPage, Uri uri) {
        PDFMapFragment pdfMapFragment = new PDFMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("map_info", mapPage);
        args.putString("uri", uri.toString());
        pdfMapFragment.setArguments(args);
        return pdfMapFragment;
    }

    public Pair<MapPage, Uri> getMap() {
        MapPage mapPage = null;
        Uri uri = null;
        if (getArguments() != null) {
            mapPage = (MapPage) getArguments().getSerializable("map_info");
            uri = Uri.parse(getArguments().getString("uri"));
        }
        return new Pair<>(mapPage, uri);
    }
}
