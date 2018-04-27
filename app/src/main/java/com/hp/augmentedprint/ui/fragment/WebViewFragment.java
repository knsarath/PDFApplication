package com.hp.augmentedprint.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hp.augmentedprint.pdfmetadata.R;

/**
 * Created on 27/4/18 by aparna .
 */
public class WebViewFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_web_view, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadUrl(getUrl());
    }

    private String getUrl() {

        if( getArguments() != null){
            return getArguments().getString("redirectedUrl");
        }
        return "null";
    }

    public static WebViewFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("redirectedUrl",url);
        WebViewFragment webViewFragment =new WebViewFragment();
        webViewFragment.setArguments(bundle);
        return webViewFragment ;
    }
}
