package com.bhaskar.sqldb;

import com.bhaskar.utils.GsonProguardMarker;

public interface SqlTypeTable extends GsonProguardMarker {
    String getPrimaryKeyColumn();
}
