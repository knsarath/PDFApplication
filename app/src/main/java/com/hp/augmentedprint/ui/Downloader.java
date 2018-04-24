package com.hp.augmentedprint.ui;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by sarath on 16/3/18.
 */

public class Downloader {
    public class ImageDownloadDispatcher {

        private final String TAG = ImageDownloadDispatcher.class.getName();
        private String mImagePath;
        private String mUrl;

        public ImageDownloadDispatcher() {
        }

        public ImageDownloadDispatcher(String url, String downloadPath) {
            this.mUrl = url;
            this.mImagePath = downloadPath;
        }

        public boolean deleteFile(String filePath) {
            Log.d(TAG, "cleanupDestination() deleting " + filePath);
            File destinationFile = new File(filePath);
            if (destinationFile.exists()) {
                return destinationFile.delete();
            }
            return false;
        }


        private boolean writeResponseBodyToDisk(ResponseBody body) {
            try {
                File futureStudioIconFile = new File(mImagePath);
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];
                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;
                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(futureStudioIconFile);
                    while (true) {
                        int read = inputStream.read(fileReader);
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(fileReader, 0, read);
                        fileSizeDownloaded += read;
                        Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                    Log.d(TAG, "file name: " + futureStudioIconFile.getPath());
                    outputStream.flush();

                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
    }
}
