package com.pratham.dse.models

import com.bhaskar.utils.GsonProguardMarker
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponseModel : GsonProguardMarker {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null
        get() {
            if (field == null) {
                field = ""
            }
            return field
        }


}