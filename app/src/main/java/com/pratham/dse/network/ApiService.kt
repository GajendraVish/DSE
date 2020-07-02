package com.angel.accessories.network


import com.pratham.dse.models.*
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList


interface ApiService {


    @GET("EXAM_APK_GET_STATES")
    fun getStates(
        @Query("APIKEY") apiKey: String?
    ): Call<ArrayList<StateModel>>

    @GET("EXAM_APK_GET_STATE_CITIES")
    fun getCity(
        @Query("APIKEY") apiKey: String?, @Query("stateid") stateId: String?
    ): Call<MutableList<CityModel>>

    @GET("EXAM_APK_CHECK_MOBILE")
    fun checkMobileNo(
        @Query("APIKEY") apiKey: String?, @Query("usertype") userType: String?,
        @Query("mobileno") mobileNo: String?
    ): Call<List<BaseResponseModel>>


    @GET("EXAM_APK_LOGIN")
    fun loginUser(
        @Query("APIKEY") apiKey: String?, @Query("usertype") userType: String?,
        @Query("mobileno") mobileNo: String?
    ): Call<List<LoginModel>>

    @FormUrlEncoded
    @POST("EXAM_APK_UPLOAD_DOCUMENT")
    fun uploadDocument(
        @Field("APIKEY") apiKey: String?, @Field("documentbasestr") documentBaseStr: String?,
        @Field("documentname") documentName: String?
    ): Call<List<BaseResponseModel>>

    @POST("EXAM_APK_REG_USER")
    fun userRegister(
        @Query("APIKEY") apiKey: String?, @Query("usertype") usertype: String?,
        @Query("firstname") firstname: String?, @Query("middlename") middlename: String?
        , @Query("lastname") lastname: String?, @Query("mobileno") mobileno: String?,
        @Query("altmobile") altmobile: String?, @Query("email") email: String?
        , @Query("stateid") stateid: String?, @Query("cityid") cityid: String?
        , @Query("address") address: String?, @Query("pincode") pincode: String?
        , @Query("uploadphoto") uploadphoto: String?, @Query("uploadaadhar") uploadaadhar: String?,
        @Query("aadhaarno") aadhaarno: String?
    ): Call<List<BaseResponseModel>>


    @GET("EXAM_APK_SEND_OTP")
    fun getOTP(
        @Query("APIKEY") apiKey: String?, @Query("emailid") emailid: String?,
        @Query("mobileno") mobileno: String?
    ): Call<List<LoginModel>>

    @GET("EXAM_APK_GET_TECH_EXAM_LIST")
    fun getTechExamList(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobileno") mobileno: String?
    ): Call<MutableList<ExamListModel>>

    @GET("EXAM_APK_GET_EXAM_SHIFTS")
    fun getTechExamShifts(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("examid") examid: String?
    ): Call<MutableList<ExamShiftModel>>

    @GET("EXAM_APK_GET_EXAM_CENTER")
    fun getTechExamCenter(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobileno") mobileNo: String?, @Query("examid") examid: String?
    ): Call<MutableList<ExamCenterModel>>

    @POST("EXAM_APK_SAVE_INSTALLATION_DATA")
    fun saveInstallationData(
        @Query("APIKEY") apiKey: String?,
        @Query("userid") userid: String?,
        @Query("mobile") mobile: String?,
        @Query("installationid") installationid: String?,
        @Query("examid") examid: String?,
        @Query("centerid") centerid: String?,
        @Query("tyecm_id") tyecm_id: String?,
        @Query("bts_100_mtr") bts_100_mtr: String?,
        @Query("bts_roof") bts_roof: String?,
        @Query("installed_big_jammer") installed_big_jammer: String?,
        @Query("installed_small_jammer") installed_small_jammer: String?,
        @Query("reserve_big_jammer") reserve_big_jammer: String?,
        @Query("reserve_small_jammer") reserve_small_jammer: String?,
        @Query("jio_signal_strength") jio_signal_strength: String?,
        @Query("airtel_signal_strength") airtel_signal_strength: String?,
        @Query("idea_signal_strength") idea_signal_strength: String?,
        @Query("other_signal_strength") other_signal_strength: String?,
        @Query("bluetooth_jamming_status") bluetooth_jamming_status: String?,
        @Query("jio_3g_jamming_status") jio_3g_jamming_status: String?,
        @Query("jio_4g_jamming_status") jio_4g_jamming_status: String?,
        @Query("airtel_3g_jamming_status") airtel_3g_jamming_status: String?,
        @Query("airtel_4g_jamming_status") airtel_4g_jamming_status: String?,
        @Query("idea_3g_jamming_status") idea_3g_jamming_status: String?,
        @Query("idea_4g_jamming_status") idea_4g_jamming_status: String?,
        @Query("other_3g_jamming_status") other_3g_jamming_status: String?,
        @Query("other_4g_jamming_status") other_4g_jamming_status: String?,
        @Query("sign_off_status") sign_off_status: String?,
        @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?,
        @Query("no_of_rooms") no_of_rooms: String?,
        @Query("no_of_flor") no_of_flor: String?,
        @Query("installationdate") installationdate: String?,
        @Query("installationtime") installationtime: String?,
        @Query("uploads") uploads: String?
    ): Call<List<BaseResponseModel>>

    @GET("EXAM_APK_GET_INSTALLATION_DATA")
    fun getInstallationData(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("tyecm_id") tyecm_id: String?
    ): Call<List<InstallationDataModel>>


    @POST("EXAM_APK_SAVE_EXAM_DEPARTURE_TIME")
    fun saveDepartureTime(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("departuretime") departuretime: String?
    ): Call<List<BaseResponseModel>>

    @POST("EXAM_APK_SAVE_EXAM_ARRIVAL_TIME")
    fun saveArrivalTime(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("arrivaltime") arrivaltime: String?
    ): Call<List<BaseResponseModel>>

    @POST("{fullUrl}")
    fun updateShiftTime(
        @Path("fullUrl") fullUrl: String,
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("time") time: String?
    ): Call<List<BaseResponseModel>>

    @POST("{fullUrl}")
    fun updateAttandance(
        @Path("fullUrl") fullUrl: String,
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("attendance") attendance: String?
    ): Call<List<BaseResponseModel>>

    @POST("{fullUrl}")
    fun updateUploads(
        @Path("fullUrl") fullUrl: String,
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("upload") upload: String?
    ): Call<List<BaseResponseModel>>




    @GET("EXAM_APK_GET_EXAM_STATUS_DATA")
    fun getExamStatusData(
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("shiftid") shiftid: String?, @Query("tyecm_id") ecm_id: String?
    ): Call<List<ExamStatusDataModel>>


    @GET("EXAM_APK_GET_USER_DETAIL")
    fun getUserDetails(
        @Query("APIKEY") apiKey: String?, @Query("mobileno") mobileno: String?,
        @Query("usertype") usertype: String?
    ): Call<List<UserProfileModel>>

    @POST("{fullUrl}")
    fun uploadRemarks(
        @Path("fullUrl") fullUrl: String,
        @Query("APIKEY") apiKey: String?, @Query("userid") userid: String?,
        @Query("mobile") mobile: String?, @Query("tyecm_id") tyecm_id: String?
        , @Query("shiftid") shiftid: String?, @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?, @Query("issue_remark") upload: String?
    ): Call<List<BaseResponseModel>>



}