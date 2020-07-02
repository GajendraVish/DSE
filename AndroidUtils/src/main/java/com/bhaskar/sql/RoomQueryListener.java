package com.bhaskar.sql;

import androidx.annotation.NonNull;

@Deprecated
public interface RoomQueryListener<T> {

    void onQuerySuccess(@NonNull T response);
    void onQueryFailed(@NonNull String message);
}
