package com.hp.augmentedprint.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.hp.augmentedprint.pdfmetadata.R;

/**
 * Created on 26/4/18 by aparna .
 */
public class WebViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(getUrl());
    }

    private String getUrl() {
//        Intent intent = getIntent();
//        if (intent != null&& intent.hasExtra("redirectUrl")) {
//            return intent.getStringExtra("redirectUrl");
//        }
        return "https://www.qr-code-generator.com/a1/?PID=1628&gclid=Cj0KCQjw8YXXBRDXARIsAMzsQuXebHgeVJTYeiO2P_EjvT6IYxM4n9e-sUU3X-w-rUQ4ISlx0jIEWiMaAn4vEALw_wcB";
    }

}
