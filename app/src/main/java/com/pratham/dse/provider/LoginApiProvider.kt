package com.angel.accessories.provider

import android.util.Log
import com.angel.accessories.network.ApiClient
import com.angel.accessories.network.ApiService
import com.pratham.dse.CeApplication
import com.pratham.dse.R
import com.pratham.dse.models.LoginModel
import com.pratham.dse.utils.AppConstant
import com.bhaskar.network.RetroApiListener
import com.bhaskar.network.RetroCallbackProvider
import com.bhaskar.utils.CommonUtils
import retrofit2.Call

class LoginApiProvider(application: CeApplication) : BaseProvider(application) {


    fun userLogin(
        userType: String, mobile: String,
        apiResponse: RetroApiListener<List<LoginModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .loginUser(AppConstant.API_KEY,userType,mobile)
                apiCall.enqueue(object : RetroCallbackProvider<List<LoginModel>>() {
                    override fun onSuccess(
                        call: Call<List<LoginModel>>,
                        response: List<LoginModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<LoginModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            }else{
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    fun getOTP(
        emailId: String?, mobile: String,
        apiResponse: RetroApiListener<List<LoginModel>>
    ) {
        mApplication?.let {
            if (CommonUtils.isNetworkAvailable(it)) {
                val apiCall =
                    ApiClient.getClient(it).create(ApiService::class.java)
                        .getOTP(AppConstant.API_KEY,emailId,mobile)
                apiCall.enqueue(object : RetroCallbackProvider<List<LoginModel>>() {
                    override fun onSuccess(
                        call: Call<List<LoginModel>>,
                        response: List<LoginModel>
                    ) {
                        Log.d(TAG, "onSuccess")
                        apiResponse.onApiSuccess(response)
                    }

                    override fun onFailed(call: Call<List<LoginModel>>, t: Throwable) {
                        Log.d(TAG, "onFailed " + t.message)
                        apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
                    }
                })
            }else{
                apiResponse.onApiFailed(it.getString(R.string.alert_api_failed))
            }
        }

    }

    companion object {

        private val TAG = LoginApiProvider::class.java.simpleName
    }

}
