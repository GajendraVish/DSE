package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class UserProfileModel : BaseResponseModel() {


    @SerializedName("tyeau_id")
    @Expose
    var userId: String? = null

    @SerializedName("tyeau_type")
    @Expose
    var userType: String? = null

    @SerializedName("tyeau_f_name")
    @Expose
    var firstName: String? = null

    @SerializedName("tyeau_l_name")
    @Expose
    var lastName: String? = null

    @SerializedName("tyeau_mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("tyeau_alt_mobile")
    @Expose
    var alternateMobile: String? = null

    @SerializedName("tyeau_email")
    @Expose
    var email: String? = null

    @SerializedName("tyeau_stateid")
    @Expose
    var stateId: String? = null

    @SerializedName("tyeau_cityid")
    @Expose
    var cityId: String? = null

    @SerializedName("tyeau_address")
    @Expose
    var address: String? = null

    @SerializedName("tyeau_pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("tyeau_upload_photo")
    @Expose
    var uploadPhoto: String? = null

    @SerializedName("tyeau_upload_aadhar")
    @Expose
    var uploadaAadhar: String? = null

    @SerializedName("tyeau_aadhar_no")
    @Expose
    var aadharNo: String? = null


}