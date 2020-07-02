package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamListModel : BaseResponseModel() {


    @SerializedName("tyem_exam_name")
    @Expose
    var examName: String? = null

    @SerializedName("tyem_agency_id")
    @Expose
    var agencyId: String? = null


    @SerializedName("tyem_id")
    @Expose
    var examId: String? = null

    @SerializedName("tyem_to_date")
    @Expose
    var examDate: String? = null

}