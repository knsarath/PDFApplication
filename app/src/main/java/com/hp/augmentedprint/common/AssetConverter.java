package com.hp.augmentedprint.common;

import android.content.Context;

import com.google.gson.Gson;
import com.hp.augmentedprint.mapschema.MapInformation;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 25/4/18 by aparna .
 */
public class AssetConverter {

    public static MapInformation loadJSONFromAsset(Context context) {
        MapInformation mapInformation = null;
        String json = null;
        try {
            InputStream is = context.getAssets().open("marker_info.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            mapInformation = gson.fromJson(json, MapInformation.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mapInformation;
    }
}
