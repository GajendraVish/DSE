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
import java.util.ArrayList

class RegistrationApiProvider(application: CeApplication) : BaseProvider(application) {


    fun getStates(
        apiResponse: RetroApiListener<ArrayList<StateModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getStates(AppConstant.API_KEY)
                apiCall.enqueue(object : RetroCallbackProvider<ArrayList<StateModel>>() {
                    override fun onSuccess(
                        call: Call<ArrayList<StateModel>>,
                        response: ArrayList<StateModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<ArrayList<StateModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun getCity(
        stateId: String,
        apiResponse: RetroApiListener<MutableList<CityModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getCity(AppConstant.API_KEY, stateId)
                apiCall.enqueue(object : RetroCallbackProvider<MutableList<CityModel>>() {
                    override fun onSuccess(
                        call: Call<MutableList<CityModel>>,
                        response: MutableList<CityModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<MutableList<CityModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            } else {
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun checkMobileNo(
        userType: String?, mobileNo: String?,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .checkMobileNo(AppConstant.API_KEY, userType, mobileNo)
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

    fun uploadDocument(
        base64Str: String, fileName: String,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .uploadDocument(AppConstant.API_KEY, base64Str, fileName)
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

    fun userRegister(
        userRegistrationModel: UserRegistrationModel,
        apiResponse: RetroApiListener<List<BaseResponseModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .userRegister(
                            AppConstant.API_KEY,
                            userRegistrationModel.userType,
                            userRegistrationModel.firstName
                            ,
                            "",
                            userRegistrationModel.lastName,
                            userRegistrationModel.mobileNo,
                            userRegistrationModel.alternateMobile,
                            userRegistrationModel.email,
                            userRegistrationModel.stateId,
                            userRegistrationModel.cityId,
                            userRegistrationModel.address,
                            userRegistrationModel.pincode,
                            userRegistrationModel.userImageName,
                            userRegistrationModel.aadhaarImageName,
                            userRegistrationModel.aadhaarnumber
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

    fun getUserDetails(
        mobileNo: String?, userType: String?,
        apiResponse: RetroApiListener<List<UserProfileModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getUserDetails(AppConstant.API_KEY, mobileNo, userType)
                apiCall.enqueue(object : RetroCallbackProvider<List<UserProfileModel>>() {
                    override fun onSuccess(
                        call: Call<List<UserProfileModel>>,
                        response: List<UserProfileModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<UserProfileModel>>, t: Throwable) {
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

        private val TAG = RegistrationApiProvider::class.java.simpleName
    }

}
