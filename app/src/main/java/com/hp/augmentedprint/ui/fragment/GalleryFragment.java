package com.hp.augmentedprint.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.schema.StoredQrCodeDetails;
import com.hp.augmentedprint.ui.adapter.GalleryAdapter;

import java.util.Objects;

/**
 * Created on 25/4/18 by aparna .
 */
public class GalleryFragment extends Fragment implements GalleryAdapter.cardViewClickLister  {
    public GalleryFragment() {
    }
    GalleryFragmentListener mGalleryFragmentListner;
    public static GalleryFragment getInstance() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initRecycleView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGalleryFragmentListner = (GalleryFragmentListener) context;
    }

    public void initRecycleView(View View) {
        View.findViewById(R.id.empty_list_test_view).setVisibility(android.view.View.GONE);
        RecyclerView recyclerView = View.findViewById(R.id.recycle_view);
        if (getStoredData() == null) {
            recyclerView.setVisibility(android.view.View.GONE);
            View.findViewById(R.id.empty_list_test_view).setVisibility(android.view.View.VISIBLE);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        GalleryAdapter adapter = new GalleryAdapter((GalleryAdapter.cardViewClickLister)this,getStoredData());
        recyclerView.setAdapter(adapter);
    }

    private StoredQrCodeDetails getStoredData(){
            SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences(
                    "PDFApplication", 0);
            String QRcodeDetail = preferences.getString("storedQRcodeDetailJson", "");
            Gson gson = new Gson();
            return gson.fromJson(String.valueOf(QRcodeDetail), StoredQrCodeDetails.class);

    }


    @Override
    public void onCardViewClick(String qrDecode) {
        mGalleryFragmentListner.launchMainActivity(qrDecode);
    }

    public interface GalleryFragmentListener {
        void launchMainActivity(String qrDecode);
    }
}
