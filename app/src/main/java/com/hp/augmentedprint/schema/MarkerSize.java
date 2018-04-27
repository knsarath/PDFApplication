package com.hp.augmentedprint.schema;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sarath on 5/3/18.
 */

public class MarkerSize implements Parcelable{
    public int height;
    public int width;

    public MarkerSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    protected MarkerSize(Parcel in) {
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<MarkerSize> CREATOR = new Creator<MarkerSize>() {
        @Override
        public MarkerSize createFromParcel(Parcel in) {
            return new MarkerSize(in);
        }

        @Override
        public MarkerSize[] newArray(int size) {
            return new MarkerSize[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
    }
}
