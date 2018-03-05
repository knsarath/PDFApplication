package com.app.pdf.pdfmetadata;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sarath on 5/3/18.
 */

public class DummyData {

    public static String readFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
