package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class LoginModel : BaseResponseModel() {

    @SerializedName("OTP")
    @Expose
    var OTP: String? = null

    @SerializedName("userid")
    @Expose
    var userId: String? = null

    @SerializedName("username")
    @Expose
    var userName: String? = null

    @SerializedName("imgpath")
    @Expose
    var imgPath: String? = null




}