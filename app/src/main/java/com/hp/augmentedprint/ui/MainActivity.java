package com.hp.augmentedprint.ui;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.augmentedprint.App;
import com.hp.augmentedprint.common.AssetConverter;
import com.hp.augmentedprint.common.BaseActivity;
import com.hp.augmentedprint.common.FragmentHelper;
import com.hp.augmentedprint.common.PdfDownloader;
import com.hp.augmentedprint.common.broadcast.AppBroadCast;
import com.hp.augmentedprint.mapschema.MapInformation;
import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.pdfmetadata.databinding.ActivityMainBinding;
import com.hp.augmentedprint.schema.MapPage;
import com.hp.augmentedprint.ui.fragment.GalleryFragment;
import com.hp.augmentedprint.ui.fragment.HomeButtonsFragment;
import com.hp.augmentedprint.ui.fragment.PDFMapFragment;
import com.hp.augmentedprint.ui.fragment.WebViewFragment;
import com.spinner.dropdown.DropDown;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements PDFMapFragment.PDFMapFragmentListener {
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
        mStringDropDown.setVisibility(View.GONE);
        initView();
        apiCall();
    }

    private void apiCall() {
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        downloadPDF();
        listenWebViewlaunch();
    }

    private void listenWebViewlaunch() {
        addToDisposable(App.mRxBus.listenFor(AppBroadCast.NotificationType.LAUNCH_WEB_VIEW)
                .filter(notification -> notification.getData() instanceof String)
                .map(notification -> (String) notification.getData())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        launchWebView(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void downloadPDF() {
        Completable.fromAction(() -> {
            String text = "Preparing Download...";
            mDownloadFileInfoTextView.setText(text);
        }).andThen(Observable.fromCallable(() -> AssetConverter.loadJSONFromAsset(getApplicationContext())))
                .flatMap(mapInformation -> Injector.getNetworkInterface().getQrCodeResult(getQrResult())
                        .flatMap(qrDetails -> {
                            mapInformation.url = qrDetails.getData().getDownloadLink();
                            mapInformation.filename = qrDetails.getData().getFileName();
                            return Observable.fromCallable(() -> mapInformation);
                        }))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((mapInformation) -> {
                    String text = "Downloading " + mapInformation.filename;
                    mDownloadFileInfoTextView.setText(text);
                })
                .observeOn(Schedulers.io())
                .concatMap(mapInformation ->
                        PdfDownloader.downloadAndSavePDF(mapInformation.url)
                                .flatMap(bytes -> Observable.fromCallable(() -> new Pair<>(mapInformation, bytes))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(mapInformationUriPair -> mStringDropDown.setVisibility(View.VISIBLE))
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
                        launchPDFMapFragment(mapPage, mapInformationPair.second);
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

    public void launchHomeButtonsFragment() {
        FragmentHelper.builder()
                .setFragment(HomeButtonsFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .popBackStack(true)
                .toContainer(R.id.container)
                .replace(true)
                .commit();
    }

    private void setStringDropDown(Pair<MapInformation, Uri> mapInformationPair, HashMap<String
            , MapPage> stringMapPageHashMap, Set<String> keySet) {
        mStringDropDown.setItems(new ArrayList<>(keySet));
        mStringDropDown.setItemClickListener(new DropDown.ItemClickListener<String>() {
            @Override
            public void onItemSelected(DropDown dropDown, String selectedItem) {
                progressBar.bringToFront();
                launchPDFMapFragment(stringMapPageHashMap.get(selectedItem), mapInformationPair.second);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        mStringDropDown.setSelectedIndex(0);
    }

    private void launchPDFMapFragment(MapPage mapPage, Uri mapInformationPair) {
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

    //TODO:change the return value
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.home:
                ReturnHomeActivity();
                return true;
            case R.id.list:
                launchGalleryFragment();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void launchGalleryFragment() {
        FragmentHelper.builder()
                .setFragment(GalleryFragment.getInstance())
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .toContainer(R.id.container)
                .replace(true)
                .commit();
    }

    private void ReturnHomeActivity() {
        launchHomeButtonsFragment();
//        FragmentHelper.clearAllFragmentFromBackStack(getSupportFragmentManager());
//        //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//        finish();
//        //startActivity(intent);
    }

    private void launchWebView(String url) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("redirectUrl", url);
        startActivity(intent);
    }

    public void launchWebViewFragment(String url) {
        mStringDropDown.setVisibility(View.GONE);
        FragmentHelper.builder()
                .setFragment(WebViewFragment.getInstance(url))
                .addToBackstack(true)
                .withFragmentManager(getSupportFragmentManager())
                .toContainer(R.id.container)
                .replace(true)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mStringDropDown.setVisibility(View.VISIBLE);
    }
}
