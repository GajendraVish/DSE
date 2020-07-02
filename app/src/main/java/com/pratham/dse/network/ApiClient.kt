package com.angel.accessories.network

import com.pratham.dse.BuildConfig
import com.pratham.dse.CeApplication
import com.pratham.dse.utils.AppConstant
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val TAG = ApiClient::class.java.simpleName

    /**
     * @param application
     * @return
     */
    private fun getOkHttpClient(application: CeApplication): OkHttpClient {

        var stethoInterceptor: StethoInterceptor? = null
        if (BuildConfig.DEBUG) {
            // set your desired log level
            stethoInterceptor = StethoInterceptor()
        }
        val okHttpBuilder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(stethoInterceptor!!)
        }

        val okHttpClient = okHttpBuilder.build()
        okHttpClient.dispatcher().maxRequests = 8
        return okHttpClient
    }

    fun getClient(application: CeApplication): Retrofit {

        return Retrofit.Builder()
            .client(getOkHttpClient(application))
            .baseUrl(AppConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
    }


}
