package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ShiftModel {



    var shiftName: String? = null
    var startTime: String? = null
    var endTime: String? = null
    var midAttendance: String? = null

    constructor(
        shiftName: String?,
        startTime: String?,
        endTime: String?,
        midAttendance: String?
    ) : super() {
        this.shiftName = shiftName
        this.startTime = startTime
        this.endTime = endTime
        this.midAttendance = midAttendance
    }
}