package com.hp.augmentedprint.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hp.augmentedprint.common.BaseActivity;
import com.hp.augmentedprint.common.FragmentHelper;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.ui.fragment.GalleryFragment;
import com.hp.augmentedprint.ui.fragment.HomeButtonsFragment;
import com.hp.augmentedprint.ui.fragment.QrCodeFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class HomeActivity extends BaseActivity implements HomeButtonsFragment.HomeButtonsFragmentListener,GalleryFragment.GalleryFragmentListener, View.OnClickListener, QrCodeFragment.QrCodeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        launchHomeButtonsFragment();
//        initView();
    }

    public void launchHomeButtonsFragment() {
        FragmentHelper.builder()
                .setFragment(HomeButtonsFragment.getInstance())
                .withFragmentManager(getSupportFragmentManager())
                .popBackStack(true)
                .toContainer(R.id.scanner_container)
                .replace(true)
                .commit();
    }

//    private void initView() {
//        Button qr_scan_btn = findViewById(R.id.qr_scan_button);
//        qr_scan_btn.setOnClickListener(this);
//        Button gallery_btn = findViewById(R.id.gallery_button);
//        gallery_btn.setOnClickListener(this);
//    }

    public void launchMainActivity(String qrResult) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("qrResult",qrResult);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.qr_scan_button:
//                launchQrScannerFragment();
//                break;
//            case R.id.gallery_button:
//                launchGalleryFragment();
//                break;
//
//        }
    }

    public void launchGalleryFragment() {
        FragmentHelper.builder()
                .setFragment(GalleryFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .popBackStack(true)
                .toContainer(R.id.scanner_container)
                .replace(true)
                .commit();
    }

    public void launchQrScannerFragment() {
        FragmentHelper.builder()
                .setFragment(QrCodeFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .toContainer(R.id.scanner_container)
                .popBackStack(true)
                .replace(true)
                .commit();
    }

    @Override
    public void onQrCodeScan(Result qrResult) {
        findViewById(R.id.qr_scan_button).setVisibility(View.VISIBLE);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        launchMainActivity(qrResult.getText());
                    } else {
                        Toast.makeText(getApplicationContext(), "Please allow Storage permission to proceed", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });


    }
}