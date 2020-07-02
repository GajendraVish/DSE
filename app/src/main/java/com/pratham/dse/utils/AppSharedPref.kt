package com.pratham.dse.utils

import android.content.Context
import android.content.SharedPreferences
import com.pratham.dse.models.UserDataModel
import com.bhaskar.utils.SharedPrefUtil
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class AppSharedPref(context: Context) {

    private val sp: SharedPreferences
    private val KEY_APP_SHARED_PREF = "APP_SHARED_PREF"
    private val KEY_USER_DATA = "key_user_data"


    init {
        sp = context.getSharedPreferences(KEY_APP_SHARED_PREF, Context.MODE_PRIVATE)
    }


    fun saveUserData(userDataModel: UserDataModel) {
        SharedPrefUtil.addObjectInGsonString(
            sp,
            KEY_USER_DATA,
            userDataModel,
            UserDataModel::class.java
        )
    }

    fun getUserData(): UserDataModel? {
        return SharedPrefUtil.getObjectFromGsonString(sp, KEY_USER_DATA, UserDataModel::class.java)
    }
    fun clearData() {
        SharedPrefUtil.clearSharedPreference(sp)
    }

}
