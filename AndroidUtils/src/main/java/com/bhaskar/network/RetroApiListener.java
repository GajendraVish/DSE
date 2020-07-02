package com.bhaskar.network;


import androidx.annotation.NonNull;

public interface RetroApiListener<T> {

    void onApiSuccess(@NonNull T response);

    void onApiFailed(@NonNull String message);
}
