package com.app.pdf.pdfmetadata.schema;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerData {
    @SerializedName("data")
    public String data;

    public MarkerData(String data) {
        this.data = data;
    }
}
