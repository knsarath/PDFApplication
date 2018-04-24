package com.hp.augmentedprint.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hp.augmentedprint.common.BaseActivity;
import com.hp.augmentedprint.common.FragmentHelper;
import com.hp.augmentedprint.pdfmetadata.R;

public class QrCodeActivity extends BaseActivity implements  View.OnClickListener, QrCodeFragment.QrCodeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner_button);
        initView();
    }

    private void initView() {
        Button qr_scan_btn = findViewById(R.id.qr_scan_button);
        qr_scan_btn.setOnClickListener(this);
    }

    private void launchMainActivity(String url) {
        Intent intent = new Intent(QrCodeActivity.this, MainActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qr_scan_button:
                view.setVisibility(View.GONE);
                launchQrScannerFragment();

        }
    }

    private void launchQrScannerFragment() {
        FragmentHelper.builder()
                .setFragment(QrCodeFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .toContainer(R.id.scanner_container)
                .commit();
    }

    @Override
    public void onQrCodeScan(String result) {
        result= "df57c958";
        findViewById(R.id.qr_scan_button).setVisibility(View.VISIBLE);
        launchMainActivity(result);
    }

}