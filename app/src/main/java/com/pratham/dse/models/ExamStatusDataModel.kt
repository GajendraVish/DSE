package com.pratham.dse.models

import android.os.Parcel
import android.os.Parcelable
import com.bhaskar.utils.ParcelableInterface
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamStatusDataModel() : BaseResponseModel(), ParcelableInterface {
    override fun readFromParcel(parcel: Parcel) {
        es_id = parcel.readString()
        departure_time = parcel.readString()
        arrival_time = parcel.readString()
        shift_one_start_time = parcel.readString()
        shift_one_end_time = parcel.readString()
        shift_one_mid_attendance = parcel.readString()
        shift_two_start_time = parcel.readString()
        shift_two_end_time = parcel.readString()
        shift_two_mid_attendance = parcel.readString()
        shift_three_start_time = parcel.readString()
        shift_three_end_time = parcel.readString()
        shift_three_mid_attendance = parcel.readString()
        shift_four_start_time = parcel.readString()
        shift_four_end_time = parcel.readString()
        shift_four_mid_attendance = parcel.readString()
        tyeassd_shift_one_upload = parcel.readString()
        tyeassd_shift_two_upload = parcel.readString()
        tyeassd_shift_three_upload = parcel.readString()
        tyeassd_shift_four_upload = parcel.readString()
        tyeassd_shift_one_remark = parcel.readString()
        tyeassd_shift_two_remark = parcel.readString()
        tyeassd_shift_three_remark = parcel.readString()
        tyeassd_shift_four_remark = parcel.readString()
    }


    @SerializedName("tyeassd_tyes_id")
    @Expose
    var es_id: String? = null

    @SerializedName("tyeassd_departure_time")
    @Expose
    var departure_time: String? = null

    @SerializedName("tyeassd_arrival_time")
    @Expose
    var arrival_time: String? = null

    @SerializedName("tyeassd_shift_one_start_time")
    @Expose
    var shift_one_start_time: String? = null

    @SerializedName("tyeassd_shift_one_end_time")
    @Expose
    var shift_one_end_time: String? = null

    @SerializedName("tyeassd_shift_one_mid_attendance")
    @Expose
    var shift_one_mid_attendance: String? = null

    @SerializedName("tyeassd_shift_two_start_time")
    @Expose
    var shift_two_start_time: String? = null

    @SerializedName("tyeassd_shift_two_end_time")
    @Expose
    var shift_two_end_time: String? = null

    @SerializedName("tyeassd_shift_two_mid_attendance")
    @Expose
    var shift_two_mid_attendance: String? = null


    @SerializedName("tyeassd_shift_three_start_time")
    @Expose
    var shift_three_start_time: String? = null

    @SerializedName("tyeassd_shift_three_end_time")
    @Expose
    var shift_three_end_time: String? = null

    @SerializedName("tyeassd_shift_three_mid_attendance")
    @Expose
    var shift_three_mid_attendance: String? = null

    @SerializedName("tyeassd_shift_four_start_time")
    @Expose
    var shift_four_start_time: String? = null

    @SerializedName("tyeassd_shift_four_end_time")
    @Expose
    var shift_four_end_time: String? = null

    @SerializedName("tyeassd_shift_four_mid_attendance")
    @Expose
    var shift_four_mid_attendance: String? = null

    @SerializedName("tyeassd_shift_one_upload")
    @Expose
    var tyeassd_shift_one_upload: String? = null

    @SerializedName("tyeassd_shift_two_upload")
    @Expose
    var tyeassd_shift_two_upload: String? = null

    @SerializedName("tyeassd_shift_three_upload")
    @Expose
    var tyeassd_shift_three_upload: String? = null

    @SerializedName("tyeassd_shift_four_upload")
    @Expose
    var tyeassd_shift_four_upload: String? = null

    @SerializedName("tyeassd_shift_one_remark")
    @Expose
    var tyeassd_shift_one_remark: String? = null

    @SerializedName("tyeassd_shift_two_remark")
    @Expose
    var tyeassd_shift_two_remark: String? = null

    @SerializedName("tyeassd_shift_three_remark")
    @Expose
    var tyeassd_shift_three_remark: String? = null

    @SerializedName("tyeassd_shift_four_remark")
    @Expose
    var tyeassd_shift_four_remark: String? = null

    constructor(parcel: Parcel) : this() {
        es_id = parcel.readString()
        departure_time = parcel.readString()
        arrival_time = parcel.readString()
        shift_one_start_time = parcel.readString()
        shift_one_end_time = parcel.readString()
        shift_one_mid_attendance = parcel.readString()
        shift_two_start_time = parcel.readString()
        shift_two_end_time = parcel.readString()
        shift_two_mid_attendance = parcel.readString()
        shift_three_start_time = parcel.readString()
        shift_three_end_time = parcel.readString()
        shift_three_mid_attendance = parcel.readString()
        shift_four_start_time = parcel.readString()
        shift_four_end_time = parcel.readString()
        shift_four_mid_attendance = parcel.readString()
        tyeassd_shift_one_upload = parcel.readString()
        tyeassd_shift_two_upload = parcel.readString()
        tyeassd_shift_three_upload = parcel.readString()
        tyeassd_shift_four_upload = parcel.readString()
        tyeassd_shift_one_remark = parcel.readString()
        tyeassd_shift_two_remark = parcel.readString()
        tyeassd_shift_three_remark = parcel.readString()
        tyeassd_shift_four_remark = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(es_id)
        parcel.writeString(departure_time)
        parcel.writeString(arrival_time)
        parcel.writeString(shift_one_start_time)
        parcel.writeString(shift_one_end_time)
        parcel.writeString(shift_one_mid_attendance)
        parcel.writeString(shift_two_start_time)
        parcel.writeString(shift_two_end_time)
        parcel.writeString(shift_two_mid_attendance)
        parcel.writeString(shift_three_start_time)
        parcel.writeString(shift_three_end_time)
        parcel.writeString(shift_three_mid_attendance)
        parcel.writeString(shift_four_start_time)
        parcel.writeString(shift_four_end_time)
        parcel.writeString(shift_four_mid_attendance)
        parcel.writeString(tyeassd_shift_one_upload)
        parcel.writeString(tyeassd_shift_two_upload)
        parcel.writeString(tyeassd_shift_three_upload)
        parcel.writeString(tyeassd_shift_four_upload)
        parcel.writeString(tyeassd_shift_one_remark)
        parcel.writeString(tyeassd_shift_two_remark)
        parcel.writeString(tyeassd_shift_three_remark)
        parcel.writeString(tyeassd_shift_four_remark)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExamStatusDataModel> {
        override fun createFromParcel(parcel: Parcel): ExamStatusDataModel {
            return ExamStatusDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ExamStatusDataModel?> {
            return arrayOfNulls(size)
        }
    }


}