package com.hp.augmentedprint.common;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import timber.log.Timber;


/**
 * Created by sarath on 15/3/18.
 */

public class PdfDownloader {
    public static Observable<Uri> downloadAndSavePDF(final String url) {
        return Observable.fromCallable(() -> {
            File file = downloadFile(url);
            return Uri.fromFile(file);
        });
    }


    static File downloadFile(String dwnload_file_path) throws IOException {
        int downloadedSize = 0, totalsize;
        float per = 0;
        File file = null;
        URL url = new URL(dwnload_file_path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        // connect
        urlConnection.connect();
        // set the path where we want to save the file
        File SDCardRoot = Environment.getExternalStorageDirectory();
        // create a new file, to save the downloaded file
        file = new File(SDCardRoot, "/pdf_map/" + System.currentTimeMillis() + ".pdf");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fileOutput = new FileOutputStream(file);
        // Stream used for reading the data from the internet
        InputStream inputStream = urlConnection.getInputStream();
        // this is the total size of the file which we are
        // downloading
        totalsize = urlConnection.getContentLength();
        // create a buffer...
        byte[] buffer = new byte[1024 * 1024];
        int bufferLength;
        while ((bufferLength = inputStream.read(buffer)) > 0) {
            fileOutput.write(buffer, 0, bufferLength);
            downloadedSize += bufferLength;
            per = ((float) downloadedSize / totalsize) * 100;
            String message = "Total PDF File size  : " + (totalsize / 1024) + " KB\n\nDownloading PDF " + (int) per + "percentage complete";
            Timber.d(message);
        }
        // close the output stream when complete //
        fileOutput.close();
        return file;
    }
}
