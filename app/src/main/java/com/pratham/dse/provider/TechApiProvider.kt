package com.angel.accessories.provider

import android.util.Log
import com.angel.accessories.network.ApiClient
import com.angel.accessories.network.ApiService
import com.pratham.dse.CeApplication
import com.pratham.dse.R
import com.pratham.dse.models.*
import com.pratham.dse.utils.AppConstant
import com.bhaskar.network.RetroApiListener
import com.bhaskar.network.RetroCallbackProvider
import com.bhaskar.utils.CommonUtils
import retrofit2.Call

class TechApiProvider(application: CeApplication) : BaseProvider(application) {


    fun getExamList(
        userId: String, mobile: String,
        apiResponse: RetroApiListener<MutableList<ExamListModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getTechExamList(AppConstant.API_KEY, userId, mobile)
                apiCall.enqueue(object : RetroCallbackProvider<MutableList<ExamListModel>>() {
                    override fun onSuccess(
                        call: Call<MutableList<ExamListModel>>,
                        response: MutableList<ExamListModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<MutableList<ExamListModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun getExamShifts(
        userId: String?, examId: String?,
        apiResponse: RetroApiListener<MutableList<ExamShiftModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getTechExamShifts(AppConstant.API_KEY, userId, examId)
                apiCall.enqueue(object : RetroCallbackProvider<MutableList<ExamShiftModel>>() {
                    override fun onSuccess(
                        call: Call<MutableList<ExamShiftModel>>,
                        response: MutableList<ExamShiftModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<MutableList<ExamShiftModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun getExamCenter(
        userId: String?, mobileNo: String?, examId: String?,
        apiResponse: RetroApiListener<MutableList<ExamCenterModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getTechExamCenter(AppConstant.API_KEY, userId, mobileNo, examId)
                apiCall.enqueue(object : RetroCallbackProvider<MutableList<ExamCenterModel>>() {
                    override fun onSuccess(
                        call: Call<MutableList<ExamCenterModel>>,
                        response: MutableList<ExamCenterModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<MutableList<ExamCenterModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }


    fun saveInstallationData(
        userid: String?,
        mobile: String?,
        installationid: String?,
        examid: String?,
        centerid: String?,
        tyecm_id: String?,
        bts_100_mtr: String?,
        bts_roof: String?,
        installed_big_jammer: String?,
        installed_small_jammer: String?,
        reserve_big_jammer: String?,
        reserve_small_jammer: String?,
        jio_signal_strength: String?,
        airtel_signal_strength: String?,
        idea_signal_strength: String?,
        other_signal_strength: String?,
        bluetooth_jamming_status: String?,
        jio_3g_jamming_status: String?,
        jio_4g_jamming_status: String?,
        airtel_3g_jamming_status: String?,
        airtel_4g_jamming_status: String?,
        idea_3g_jamming_status: String?,
        idea_4g_jamming_status: String?,
        other_3g_jamming_status: String?,
        other_4g_jamming_status: String?,
        sign_off_status: String?,
        latitude: String?,
        longitude: String?,
        no_of_rooms: String?,
        no_of_flor: String?,
        installationdate: String?,
        installationtime: String?,
        uploads: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .saveInstallationData(
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            installationid,
                            examid,
                            centerid,
                            tyecm_id,
                            bts_100_mtr,
                            bts_roof,
                            installed_big_jammer,
                            installed_small_jammer,
                            reserve_big_jammer,
                            reserve_small_jammer,
                            jio_signal_strength,
                            airtel_signal_strength,
                            idea_signal_strength,
                            other_signal_strength,
                            bluetooth_jamming_status,
                            jio_3g_jamming_status,
                            jio_4g_jamming_status,
                            airtel_3g_jamming_status,
                            airtel_4g_jamming_status,
                            idea_3g_jamming_status,
                            idea_4g_jamming_status,
                            other_3g_jamming_status,
                            other_4g_jamming_status,
                            sign_off_status,
                            latitude,
                            longitude,
                            no_of_rooms,
                            no_of_flor,
                            installationdate,
                            installationtime,
                            uploads
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun getInstalationData(
        userId: String?, ecmId: String?,
        apiResponse: RetroApiListener<List<InstallationDataModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getInstallationData(AppConstant.API_KEY, userId, ecmId)
                apiCall.enqueue(object : RetroCallbackProvider<List<InstallationDataModel>>() {
                    override fun onSuccess(
                        call: Call<List<InstallationDataModel>>,
                        response: List<InstallationDataModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<InstallationDataModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun saveDepartureTime(
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        departuretime: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .saveDepartureTime(
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftid,
                            latitude,
                            longtitude,
                            departuretime
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }


    fun saveArrivalTime(
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftId: String?, latitude: String?, longtitude: String?,
        arrivalTime: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .saveArrivalTime(
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftId,
                            latitude,
                            longtitude,
                            arrivalTime
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun updateShiftTime(
        fulUrl: String,
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftId: String?, latitude: String?, longtitude: String?,
        time: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .updateShiftTime(
                            fulUrl,
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftId,
                            latitude,
                            longtitude,
                            time
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun updateAttancance(
        fulUrl: String,
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftId: String?, latitude: String?, longtitude: String?,
        attandance: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .updateAttandance(
                            fulUrl,
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftId,
                            latitude,
                            longtitude,
                            attandance
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }


    fun updateUploads(
        fulUrl: String,
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftId: String?, latitude: String?, longtitude: String?,
        fileName: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .updateUploads(
                            fulUrl,
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftId,
                            latitude,
                            longtitude,
                            fileName
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }
    fun getExamStatus(
        userId: String?, shiftId: String?,ecmId: String?,
        apiResponse: RetroApiListener<List<ExamStatusDataModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getExamStatusData(AppConstant.API_KEY, userId, shiftId,ecmId)
                apiCall.enqueue(object : RetroCallbackProvider<List<ExamStatusDataModel>>() {
                    override fun onSuccess(
                        call: Call<List<ExamStatusDataModel>>,
                        response: List<ExamStatusDataModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<ExamStatusDataModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun remarkIssue(
        fulUrl: String,
        userid: String?,
        mobile: String?, tyecm_id: String?,
        shiftId: String?, latitude: String?, longtitude: String?,
        remark: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .uploadRemarks(
                            fulUrl,
                            AppConstant.API_KEY,
                            userid,
                            mobile,
                            tyecm_id,
                            shiftId,
                            latitude,
                            longtitude,
                            remark
                        )
                apiCall.enqueue(object : RetroCallbackProvider<List<BaseResponseModel>>() {
                    override fun onSuccess(
                        call: Call<List<BaseResponseModel>>,
                        response: List<BaseResponseModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<BaseResponseModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    companion object {

        private val TAG = TechApiProvider::class.java.simpleName
    }

}
