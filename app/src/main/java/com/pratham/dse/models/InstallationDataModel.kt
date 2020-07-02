package com.pratham.dse.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class InstallationDataModel: BaseResponseModel() {

    @SerializedName("tyeaid_id")
    @Expose
    var installationId: String? = null

    @SerializedName("tyeaid_tyem_id")
    @Expose
    var em_id: String? = null

    @SerializedName("tyeaid_tyec_id")
    @Expose
    var ec_id: String? = null

    @SerializedName("tyeaid_tyecm_id")
    @Expose
    var ecm_id: String? = null

    @SerializedName("tyeaid_date")
    @Expose
    var date: String? = null

    @SerializedName("tyeaid_time")
    @Expose
    var time: String? = null

    @SerializedName("tyeaid_bts_100_mtr")
    @Expose
    var bts_100_mtr: String? = null

    @SerializedName("tyeaid_bts_roof")
    @Expose
    var bts_roof: String? = null

    @SerializedName("tyeaid_installed_big_jammer")
    @Expose
    var installed_big_jammer: String? = null

    @SerializedName("tyeaid_installed_small_jammer")
    @Expose
    var installed_small_jammer: String? = null

    @SerializedName("tyeaid_reserve_big_jammer")
    @Expose
    var reserve_big_jammer: String? = null

    @SerializedName("tyeaid_reserve_small_jammer")
    @Expose
    var reserve_small_jammer: String? = null

    @SerializedName("tyeaid_jio_signal_strength")
    @Expose
    var jio_signal_strength: String? = null

    @SerializedName("tyeaid_airtel_signal_strength")
    @Expose
    var airtel_signal_strength: String? = null

    @SerializedName("tyeaid_idea_signal_strength")
    @Expose
    var idea_signal_strength: String? = null

    @SerializedName("tyeaid_other_signal_strength")
    @Expose
    var other_signal_strength: String? = null

    @SerializedName("tyeaid_bluetooth_jamming_status")
    @Expose
    var bluetooth_jamming_status: String? = null

    @SerializedName("tyeaid_jio_3g_jamming_status")
    @Expose
    var jio_3g_jamming_status: String? = null

    @SerializedName("tyeaid_jio_4g_jamming_status")
    @Expose
    var jio_4g_jamming_status: String? = null

    @SerializedName("tyeaid_airtel_3g_jamming_status")
    @Expose
    var airtel_3g_jamming_status: String? = null

    @SerializedName("tyeaid_airtel_4g_jamming_status")
    @Expose
    var airtel_4g_jamming_status: String? = null

    @SerializedName("tyeaid_idea_3g_jamming_status")
    @Expose
    var idea_3g_jamming_status: String? = null

    @SerializedName("tyeaid_idea_4g_jamming_status")
    @Expose
    var idea_4g_jamming_status: String? = null

    @SerializedName("tyeaid_other_3g_jamming_status")
    @Expose
    var other_3g_jamming_status: String? = null

    @SerializedName("tyeaid_other_4g_jamming_status")
    @Expose
    var other_4g_jamming_status: String? = null

    @SerializedName("tyeaid_sign_off_status")
    @Expose
    var sign_off_status: String? = null

    @SerializedName("tyeaid_upload")
    @Expose
    var uploads: String? = null

    @SerializedName("tyeaid_no_of_flor")
    @Expose
    var no_of_flor: String? = null

    @SerializedName("tyeaid_no_of_rooms")
    @Expose
    var no_of_rooms: String? = null


}