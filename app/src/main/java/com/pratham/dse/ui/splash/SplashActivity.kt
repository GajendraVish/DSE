package com.pratham.dse.ui.splash

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.crashlytics.android.Crashlytics
import com.pratham.dse.R
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.TechMainActivity
import com.pratham.dse.ui.admin.AdminMainActivity
import com.pratham.dse.ui.login.LoginActivity
import com.pratham.dse.ui.sc.SCMainActivity
import com.pratham.dse.utils.AppConstant

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class SplashActivity : BaseActivity() {
    private var handler: Handler? = null

    companion object {
        private const val SPLASH_DISPLAY_LENGTH: Long = 3000
        private val TAG = SplashActivity::class.java.simpleName
    }

    init {
        handler = Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler?.postDelayed({
            /* Create an Intent that will start the Main-Activity. */
            val userDataModel = mAppSharedPref?.getUserData()
            if (userDataModel != null && !TextUtils.isEmpty(userDataModel.userId)) {
                when {
                    userDataModel.userType == AppConstant.TYPE_ADMIN -> {
                        AdminMainActivity.startActivity(mBaseActivity, null)
                    }
                    userDataModel.userType == AppConstant.TYPE_TECHNICIAN -> {
                        TechMainActivity.startActivity(mBaseActivity, null)
                    }
                    userDataModel.userType == AppConstant.TYPE_SC -> {
                        SCMainActivity.startActivity(mBaseActivity, null)
                    }
                }
            } else {
                LoginActivity.startActivity(this@SplashActivity, null)
            }
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }

    override fun onDestroy() {
        handler?.removeCallbacksAndMessages(null);
        super.onDestroy()

    }
}
