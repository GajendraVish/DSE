package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class StateModel: BaseResponseModel() {


    @SerializedName("tyesm_id")
    @Expose
    var stateId: String? = null

    @SerializedName("tyesm_state")
    @Expose
    var stateName: String? = null

}