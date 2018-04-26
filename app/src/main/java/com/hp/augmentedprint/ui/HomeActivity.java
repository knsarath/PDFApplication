package com.hp.augmentedprint.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;
import com.hp.augmentedprint.common.BaseActivity;
import com.hp.augmentedprint.common.FragmentHelper;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.ui.fragment.GalleryFragment;
import com.hp.augmentedprint.ui.fragment.QrCodeFragment;

public class HomeActivity extends BaseActivity implements GalleryFragment.GalleryFragmentListener, View.OnClickListener, QrCodeFragment.QrCodeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Button qr_scan_btn = findViewById(R.id.qr_scan_button);
        qr_scan_btn.setOnClickListener(this);
        Button gallery_btn = findViewById(R.id.gallery_button);
        gallery_btn.setOnClickListener(this);
    }

    public void launchMainActivity(String qrResult) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("qrResult",qrResult);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qr_scan_button:
                launchQrScannerFragment();
                break;
            case R.id.gallery_button:
                launchGalleryFragment();
                break;

        }
    }

    private void launchGalleryFragment() {
        FragmentHelper.builder()
                .setFragment(GalleryFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .popBackStack(true)
                .toContainer(R.id.scanner_container)
                .replace(true)
                .commit();
    }



    private void launchQrScannerFragment() {
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
        launchMainActivity(qrResult.getText());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}