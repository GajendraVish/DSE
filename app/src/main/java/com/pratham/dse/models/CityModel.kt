package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class CityModel: BaseResponseModel() {


    @SerializedName("tyecim_id")
    @Expose
    var cityId: String? = null

    @SerializedName("tyecim_tyesm_id")
    @Expose
    var stateId: String? = null

    @SerializedName("tyecim_city")
    @Expose
    var cityName: String? = null

}