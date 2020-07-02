package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamCenterModel : BaseResponseModel() {


    @SerializedName("tyec_id")
    @Expose
    var ecId: String? = null

    @SerializedName("tyec_name")
    @Expose
    var centerName: String? = null

    @SerializedName("tyecm_id")
    @Expose
    var ecmId: String? = null

}