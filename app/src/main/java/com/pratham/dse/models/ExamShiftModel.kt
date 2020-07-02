package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamShiftModel : BaseResponseModel() {


    @SerializedName("tyesd_id")
    @Expose
    var sdId: String? = null

    @SerializedName("tyesd_tyem_id")
    @Expose
    var examId: String? = null


    @SerializedName("tyesd_exam_date")
    @Expose
    var examDate: String? = null

    @SerializedName("tyesd_shift_one_starttime")
    @Expose
    var shiftOneStartTime: String? = null

    @SerializedName("tyesd_shift_one_endtime")
    @Expose
    var shiftOneEndTime: String? = null

    @SerializedName("tyesd_shift_two_starttime")
    @Expose
    var shiftTwoStartTime: String? = null

    @SerializedName("tyesd_shift_two_endtime")
    @Expose
    var shiftTwoEndTime: String? = null

    @SerializedName("tyesd_shift_three_starttime")
    @Expose
    var shiftThreeStartTime: String? = null

    @SerializedName("tyesd_shift_three_endtime")
    @Expose
    var shiftThreeEndTime: String? = null

    @SerializedName("tyesd_shift_four_starttime")
    @Expose
    var shiftFourStartTime: String? = null

    @SerializedName("tyesd_shift_four_endtime")
    @Expose
    var shiftFourEndTime: String? = null


}