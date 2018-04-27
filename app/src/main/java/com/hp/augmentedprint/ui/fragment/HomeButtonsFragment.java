package com.hp.augmentedprint.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hp.augmentedprint.pdfmetadata.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created on 26/4/18 by aparna .
 */
public class HomeButtonsFragment extends Fragment implements View.OnClickListener {
    private HomeButtonsFragmentListener mHomeButtonsFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeButtonsFragmentListener = (HomeButtonsFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_buttons, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button qr_scan_btn = view.findViewById(R.id.qr_scan_button);
        qr_scan_btn.setOnClickListener(this);
        Button gallery_btn = view.findViewById(R.id.gallery_button);
        gallery_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qr_scan_button:
                new RxPermissions(this.getActivity())
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {
                                mHomeButtonsFragmentListener.launchQrScannerFragment();
                            } else {
                                Toast.makeText(getContext(), "Please enable camra permission to Scan QR code", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.gallery_button:
                mHomeButtonsFragmentListener.launchGalleryFragment();
                break;

        }
    }

    public static HomeButtonsFragment getInstance() {
        return new HomeButtonsFragment();
    }

    public interface HomeButtonsFragmentListener {
        void launchGalleryFragment();

        void launchQrScannerFragment();

    }
}
