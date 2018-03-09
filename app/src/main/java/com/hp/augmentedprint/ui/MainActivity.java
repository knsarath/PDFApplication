package com.hp.augmentedprint.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hp.augmentedprint.DummyData;
import com.hp.augmentedprint.MarkerClickListener;
import com.hp.augmentedprint.MarkerDrawer;
import com.hp.augmentedprint.MarkerDrawing;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.pdfmetadata.databinding.ActivityMainBinding;
import com.hp.augmentedprint.schema.MapPage;
import com.hp.augmentedprint.schema.MarkerInfo;
import com.hp.augmentedprint.schema.MarkerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MarkerClickListener {
    private MarkerDrawing mMarkerDrawing;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        loadPDF();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    private void loadPDF() {
        mMarkerDrawing = new MarkerDrawer(mBinding.pdfView);
        Type type = new TypeToken<List<MapPage>>() {
        }.getType();
        String json = DummyData.readFromAssets(this.getApplicationContext(), "marker_info.json");
        List<MapPage> mapInformation = new Gson().fromJson(json, type);

        String assetName = "combined.pdf";
        assetName = "map.pdf";


        mBinding.pdfView.fromAsset(assetName)
                .onPageChange((pagenum, pageCount) -> {
                    Log.d(TAG, "onPageChange: " + pagenum);
                    int maxIndex = mapInformation.size() - 1;
                    if (pagenum <= maxIndex) {
                        List<MarkerInfo> markerInfos = mapInformation.get(pagenum).mMarkerInfos;
                        mMarkerDrawing.placeMarkers(markerInfos);
                    } else {
                        mMarkerDrawing.clearMarkers();
                    }
                })
                .onDraw((canvas, pageWidth, pageHeight, displayedPage) -> mMarkerDrawing.onZoom(canvas, pageWidth, pageHeight))
                .load();
        mMarkerDrawing.setMarkerClickListener(this);
    }

    @Override
    public void onMarkerClicked(MarkerView markerView, MarkerInfo markerInfo) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Info")
                .setMessage(markerInfo.mMarkerData.data)
                .setPositiveButton("OK", this::dismiss)
                .setNegativeButton("CANCEL", this::dismiss)
                .show();
    }

    private void dismiss(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMarkerDrawing.destroy();
        mBinding.unbind();
    }
}
