package com.hp.augmentedprint.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.schema.StoredQrCodeDetail;
import com.hp.augmentedprint.schema.StoredQrCodeDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

/**
 * Created on 24/4/18 by aparna .
 */
public class QrCodeFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private QrCodeListener mQrCodeListener;

    public interface QrCodeListener{
        public void onQrCodeScan(Result result);
    }
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;

    public static QrCodeFragment getInstance() {
        return new QrCodeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        initView(view);
        checkPermission();
        scanView();
        return view;
    }

    private void initView(View view) {
        mScannerView = view.findViewById(R.id.ScannerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mQrCodeListener= (QrCodeListener) context;
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext())
                , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "permision denied", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity())
                    , new String[]{CAMERA}, REQUEST_CAMERA);
        }
    }

    @RequiresPermission(CAMERA)
    private void scanView() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    public void handleResult(Result result) {
        if (mQrCodeListener != null) {
            mQrCodeListener.onQrCodeScan(result);
        }
        storeResultToSharedPreferences(result);
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    private void storeResultToSharedPreferences(Result qrResult) {
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences(
                "PDFApplication", 0);
        ArrayList<StoredQrCodeDetail> list = ConvertToList(preferences.getString("storedQRcodeDetailJson",""));
        StoredQrCodeDetail qrCodeDetail = new StoredQrCodeDetail(qrResult.getText(),getDateTime());
        if (list != null) {
            list.add(qrCodeDetail);
        }else{
            list = new ArrayList<StoredQrCodeDetail>();
            list.add(qrCodeDetail);
        }
        StoredQrCodeDetails storedQrCodeDetails = new StoredQrCodeDetails(list);
        Gson gson = new Gson();
        String data = gson.toJson(storedQrCodeDetails);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("storedQRcodeDetailJson",data);
        editor.apply();
    }

    private ArrayList<StoredQrCodeDetail> ConvertToList(String storedQRCodeDetailJson) {
        if(storedQRCodeDetailJson.equalsIgnoreCase("")){
            return null;
        }
        Gson gson = new Gson();
        StoredQrCodeDetails storedQrCodeDetails = gson.fromJson(String.valueOf(storedQRCodeDetailJson), StoredQrCodeDetails.class);
        return storedQrCodeDetails.getStoredQrCodeDetails();

    }


    private String getDateTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE\nd MMM yyyy\nh:mm a");
        return sdf.format( currentTime);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
        scanView();

    }
}
