package com.bhaskar.sqldb;

import androidx.annotation.NonNull;

public interface SqlQueryListener<T> {

    void onQuerySuccess(@NonNull T response);
    void onQueryFailed(@NonNull String message);
}
