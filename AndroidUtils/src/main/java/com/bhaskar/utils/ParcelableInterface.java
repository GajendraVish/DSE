package com.bhaskar.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public interface ParcelableInterface extends Parcelable, GsonProguardMarker {

    void readFromParcel(@NonNull Parcel source);
}
