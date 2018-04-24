package com.hp.augmentedprint.ui;


import android.Manifest;
import android.content.Context;
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

import com.google.zxing.Result;
import com.hp.augmentedprint.pdfmetadata.R;

import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

/**
 * Created on 24/4/18 by aparna .
 */
public class QrCodeFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private QrCodeListener mQrCodeListener;

    public interface QrCodeListener{
        public void onQrCodeScan(String result);
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
        Toast.makeText(getContext(), result.getText(), Toast.LENGTH_LONG).show();
        if (mQrCodeListener != null) {
            mQrCodeListener.onQrCodeScan(result.getText());
        }
        Objects.requireNonNull(getActivity()).onBackPressed();
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
