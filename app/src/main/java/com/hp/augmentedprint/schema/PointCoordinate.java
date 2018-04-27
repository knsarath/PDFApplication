package com.hp.augmentedprint.schema;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sarath on 5/3/18.
 */

public class PointCoordinate implements Parcelable {
    public float leftPercentage;
    public float topPercentage;

    public PointCoordinate(float leftPercentage, float topPercentage) {
        this.leftPercentage = leftPercentage;
        this.topPercentage = topPercentage;
    }

    protected PointCoordinate(Parcel in) {
        leftPercentage = in.readFloat();
        topPercentage = in.readFloat();
    }

    public static final Creator<PointCoordinate> CREATOR = new Creator<PointCoordinate>() {
        @Override
        public PointCoordinate createFromParcel(Parcel in) {
            return new PointCoordinate(in);
        }

        @Override
        public PointCoordinate[] newArray(int size) {
            return new PointCoordinate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(leftPercentage);
        dest.writeFloat(topPercentage);
    }
}
