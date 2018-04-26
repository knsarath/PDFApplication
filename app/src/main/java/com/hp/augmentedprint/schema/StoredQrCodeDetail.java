package com.hp.augmentedprint.schema;

/**
 * Created on 25/4/18 by aparna .
 */

public class StoredQrCodeDetail {

    private String mDecodeData;

    private String mScannedDate;

//    @Ignore
//    Bitmap picture;


    public StoredQrCodeDetail(String decodeData,String scannedDate) {
        this.mDecodeData = decodeData;
        this.mScannedDate = scannedDate;
    }

    public StoredQrCodeDetail() {
    }

    public String getDecodeData() {
        return mDecodeData;
    }

    public String getScannedDate() {
        return mScannedDate;
    }

    public void setDecodeData(String mDecodeData) {
        this.mDecodeData = mDecodeData;
    }

    public void setScannedDate(String mScannedDate) {
        this.mScannedDate = mScannedDate;
    }
}
