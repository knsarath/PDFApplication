package com.hp.augmentedprint.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hp.augmentedprint.common.AssetConverter;
import com.hp.augmentedprint.common.FragmentHelper;
import com.hp.augmentedprint.common.PdfDownloader;
import com.hp.augmentedprint.mapschema.MapInformation;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.pdfmetadata.databinding.ActivityMainBinding;
import com.hp.augmentedprint.schema.MapPage;
import com.spinner.dropdown.DropDown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private DropDown<String> mStringDropDown;
    private ProgressBar progressBar;
    private TextView mDownloadFileInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        apiCall();
    }

    private void apiCall() {
        final String[] fileName = new String[1];
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Observable.fromCallable(() -> AssetConverter.loadJSONFromAsset(getApplicationContext()))
                .flatMap(mapInformation -> Injector.getNetworkInterface().getQrCodeResult(getQrResult())
                        .flatMap(qrDetails -> {
                            mapInformation.url = qrDetails.getData().getDownloadLink();
                            fileName[0] =qrDetails.getData().getFileName();
                            return Observable.fromCallable(() -> mapInformation);
                        }))
                .doOnNext((MapInformation mapInformation) -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text = "downloading " + fileName[0];
                            mDownloadFileInfoTextView.setText(text);
                        }
                    });
                })
                .concatMap(mapInformation ->
                        PdfDownloader.downloadAndSavePDF(mapInformation.url)
                                .flatMap(bytes -> Observable.fromCallable(() -> new Pair<>(mapInformation, bytes))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Pair<MapInformation, Uri>>() {
                    @Override
                    public void onNext(Pair<MapInformation, Uri> mapInformationPair) {
                        Timber.d("onNext: " + mapInformationPair.first);
                        MapInformation mapInformation = mapInformationPair.first;
                        MapPage mapPage = null;
                        HashMap<String, MapPage> stringMapPageHashMap = new LinkedHashMap<>();
                        for (HashMap<String, MapPage> map : mapInformation.mapInfo) {
                            stringMapPageHashMap = map;
                            break;
                        }
                        Set<String> keySet = stringMapPageHashMap.keySet();
                        for (String key : keySet) {
                            mapPage = stringMapPageHashMap.get(key);
                            break;
                        }
                        mStringDropDown.setItems(new ArrayList<>(keySet));
                        mStringDropDown.setItemClickListener(new DropDown.ItemClickListener<String>() {
                            @Override
                            public void onItemSelected(DropDown dropDown, String selectedItem) {

                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });

                        if (mapPage != null) {
                            FragmentHelper.builder()
                                    .withFragmentManager(getSupportFragmentManager())
                                    .toContainer(R.id.container)
                                    .setFragment(PDFMapFragment.createInstance(mapPage, mapInformationPair.second))
                                    .withAnimation(false)
                                    .commit();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("on Complete");
                    }
                });
    }

    private void initView() {
        mStringDropDown = mBinding.dropDown;
        progressBar = findViewById(R.id.download_file_progress_bar);
        mDownloadFileInfoTextView = findViewById(R.id.download_file_info_text_view);

    }

    private String getQrResult() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("qrResult")) {
            return intent.getStringExtra("qrResult");
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
    }
}
