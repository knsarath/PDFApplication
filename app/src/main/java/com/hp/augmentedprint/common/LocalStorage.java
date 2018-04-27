package com.hp.augmentedprint.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hp.augmentedprint.schema.StoredQrCodeDetail;
import com.hp.augmentedprint.schema.StoredQrCodeDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class LocalStorage {
    public static void storeResultToSharedPreferences(Context context, String name) {
        SharedPreferences preferences = Objects.requireNonNull(context).getSharedPreferences(
                "PDFApplication", 0);
        ArrayList<StoredQrCodeDetail> list = ConvertToList(preferences.getString("storedQRcodeDetailJson", ""));
        StoredQrCodeDetail qrCodeDetail = new StoredQrCodeDetail(name, getDateTime());
        if (list != null) {
            list.add(qrCodeDetail);
        } else {
            list = new ArrayList<StoredQrCodeDetail>();
            list.add(qrCodeDetail);
        }
        StoredQrCodeDetails storedQrCodeDetails = new StoredQrCodeDetails(list);
        Gson gson = new Gson();
        String data = gson.toJson(storedQrCodeDetails);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("storedQRcodeDetailJson", data);
        editor.apply();
    }

    public static ArrayList<StoredQrCodeDetail> ConvertToList(String storedQRCodeDetailJson) {
        if (storedQRCodeDetailJson.equalsIgnoreCase("")) {
            return null;
        }
        Gson gson = new Gson();
        StoredQrCodeDetails storedQrCodeDetails = gson.fromJson(String.valueOf(storedQRCodeDetailJson), StoredQrCodeDetails.class);
        return storedQrCodeDetails.getStoredQrCodeDetails();

    }


    public static String getDateTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy \nh:mm a");
        return sdf.format(currentTime);
    }
}
