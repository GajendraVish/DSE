package com.pratham.dse.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.pratham.dse.R
import com.pratham.dse.adaptor.SpinnerCustomAdaptor
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class LoginActivity : BaseActivity() {
    private var userTypes: Array<String>? = null

    companion object {
        fun startActivity(context: Activity, bundle: Bundle?) {
            val intent = Intent(context, LoginActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
    }

    private fun initUI() {
        userTypes = resources.getStringArray(R.array.userTypes)
        userTypes?.let {
            userTypeSpinner.adapter =
                SpinnerCustomAdaptor(mBaseActivity, it)
        }
        tvSignup.setOnClickListener {
            RegistrationStep1Activity.startActivity(this@LoginActivity, null)
        }
        btnLogin.setOnClickListener {
            val mobile = edMobile.text.toString().trim()
            val userType = userTypeSpinner.selectedItemPosition.toString()
            if (userTypeSpinner.selectedItemPosition == 0) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_select_user),
                    Toast.LENGTH_LONG
                ).show()
            } else if (mobile.length < 10) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.msg_valid_mobile),
                    Toast.LENGTH_LONG
                ).show()
                edMobile.requestFocus()
            } else {
                val bundle = Bundle()
                bundle.putString(BundleConstant.KEY_MOBILE, mobile)
                bundle.putString(BundleConstant.KEY_USER_TYPE, userType)
                bundle.putString(BundleConstant.KEY_FROM, BundleConstant.LOGIN)
                OTPActivity.startActivityForResult(
                    this@LoginActivity,
                    bundle,
                    AppConstant.OTP_ACTIVITY_FLAG
                )
            }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.OTP_ACTIVITY_FLAG && resultCode == Activity.RESULT_OK) {
            finish()
        }
    }

}
