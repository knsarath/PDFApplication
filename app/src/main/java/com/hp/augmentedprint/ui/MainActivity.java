package com.hp.augmentedprint.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        mStringDropDown = mBinding.dropDown;
        initView();
        apiCall();
    }

    private void apiCall() {
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Observable.fromCallable(() -> AssetConverter.loadJSONFromAsset(getApplicationContext()))
                .flatMap(mapInformation -> Injector.getNetworkInterface().getQrCodeResult(getQrResult())
                        .flatMap(qrDetails -> {
                            mapInformation.url = qrDetails.getData().getDownloadLink();
                            mapInformation.filename =qrDetails.getData().getFileName();
                            return Observable.fromCallable(() -> mapInformation);
                        }))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((mapInformation) -> {
                    String text = "\n\n\n\ndownloading " + mapInformation.filename;
                    mDownloadFileInfoTextView.setText(text);
                })
                .observeOn(Schedulers.io())
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
                        setStringDropDown(mapInformationPair, stringMapPageHashMap, keySet);
                        launchPDFMapFragment(mapPage , mapInformationPair.second);
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

    private void setStringDropDown(Pair<MapInformation, Uri> mapInformationPair, HashMap<String
            , MapPage> stringMapPageHashMap, Set<String> keySet) {
        mStringDropDown.setItems(new ArrayList<>(keySet));
        mStringDropDown.setItemClickListener(new DropDown.ItemClickListener<String>() {
            @Override
            public void onItemSelected(DropDown dropDown, String selectedItem) {
                Toast.makeText(getApplicationContext(),selectedItem,Toast.LENGTH_LONG).show();
                launchPDFMapFragment(stringMapPageHashMap.get(selectedItem), mapInformationPair.second);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void launchPDFMapFragment(MapPage mapPage, Uri mapInformationPair ) {
        if (mapPage != null) {
            FragmentHelper.builder()
                    .withFragmentManager(getSupportFragmentManager())
                    .toContainer(R.id.container)
                    .setFragment(PDFMapFragment.createInstance(mapPage, mapInformationPair))
                    .withAnimation(false)
                    .commit();
        }
    }

    private void initView() {

        progressBar = findViewById(R.id.download_file_progress_bar);
        mDownloadFileInfoTextView = findViewById(R.id.download_file_info_text_view);

    }

    private String getQrResult() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("qrResult")) {
//            return intent.getStringExtra("qrResult");
        }
        return "df57c958";
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.home:
                ReturnHomeActivity();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void ReturnHomeActivity() {

    }
}
