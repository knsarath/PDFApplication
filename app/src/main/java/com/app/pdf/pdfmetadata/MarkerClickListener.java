package com.app.pdf.pdfmetadata;

import com.app.pdf.pdfmetadata.schema.MarkerInfo;
import com.app.pdf.pdfmetadata.schema.MarkerView;

/**
 * Created by sarath on 5/3/18.
 */

public interface MarkerClickListener {
    void onMarkerClicked(MarkerView markerView, MarkerInfo markerInfo);
}
