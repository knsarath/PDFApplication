package com.hp.augmentedprint.schema;

import java.util.ArrayList;

/**
 * Created on 25/4/18 by aparna .
 */
public class StoredQrCodeDetails {
    private ArrayList<StoredQrCodeDetail> mStoredQrCodeDetails;

    public StoredQrCodeDetails(ArrayList<StoredQrCodeDetail> list) {
        mStoredQrCodeDetails=list;
    }


    public ArrayList<StoredQrCodeDetail> getStoredQrCodeDetails() {
        return mStoredQrCodeDetails;
    }

    public void setStoredQrCodeDetails(ArrayList<StoredQrCodeDetail> mStoredQrCodeDetails) {
        this.mStoredQrCodeDetails = mStoredQrCodeDetails;
    }
}
