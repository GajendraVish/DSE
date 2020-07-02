package com.pratham.dse.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.angel.accessories.provider.LoginApiProvider
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.pratham.dse.R
import com.pratham.dse.models.LoginModel
import com.pratham.dse.models.UserDataModel
import com.pratham.dse.models.UserRegistrationModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.TechMainActivity
import com.pratham.dse.ui.admin.AdminMainActivity
import com.pratham.dse.ui.sc.SCMainActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import kotlinx.android.synthetic.main.activity_otp.*


/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class OTPActivity : BaseActivity() {

    private var mMobileNo: String? = null
    private var mUserName: String? = null
    private var mUserImagePath: String? = null
    private var mEmailId: String? = null
    private var mOtp: String? = null
    private var mUserId: String? = null
    private var mUserType: String? = null
    private var mCallFrom: String? = null
    private var mUserRegistrationModel: UserRegistrationModel? = null
    private var mCountDowntimer: CountDownTimer? = null
    private var isIntiView=false

    companion object {

        fun startActivity(context: Context, bundle: Bundle?) {

            val intent = Intent(context, OTPActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }

        fun startActivityForResult(context: Activity, bundle: Bundle?, flag: Int) {
            val intent = Intent(context, OTPActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivityForResult(intent, flag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readDataFromBundle()

    }

    private fun initView() {
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_otp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
    }

    private fun readDataFromBundle() {

        mCallFrom = intent.getStringExtra(BundleConstant.KEY_FROM)

        if (!TextUtils.isEmpty(mCallFrom) && mCallFrom == BundleConstant.LOGIN) {
            mMobileNo = intent.getStringExtra(BundleConstant.KEY_MOBILE)
            mUserType = intent.getStringExtra(BundleConstant.KEY_USER_TYPE)
            userLogin(mUserType!!, mMobileNo!!)
        } else {
            mUserRegistrationModel =
                intent.getParcelableExtra(BundleConstant.KEY_REG_USER) as UserRegistrationModel
            mUserRegistrationModel?.let {
                mMobileNo = it.mobileNo
                mUserType = it.userType
                mEmailId = it.email
                getOTP(it.email, it.mobileNo!!)

            }
        }


    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_otp)
        mMobileNo?.let {
            tvMobile.text = it
        }
        tvResendOtp.setOnClickListener {
            if (!TextUtils.isEmpty(mCallFrom) && mCallFrom == BundleConstant.LOGIN) {
                userLogin(mUserType!!, mMobileNo!!)
            } else {
                getOTP(mEmailId, mMobileNo!!)
            }
        }
        tvBtnSubmit.setOnClickListener {
            val otp = otpView.text.toString()
            if (!cbTandC.isChecked) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_accept_tc),
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(otp)) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_enter_otp),
                    Toast.LENGTH_LONG
                ).show()
                otpView.requestFocus()
            } else if (otp != mOtp) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_valid_otp),
                    Toast.LENGTH_LONG
                ).show()
                otpView.requestFocus()
            } else {
                if (!TextUtils.isEmpty(mCallFrom) && mCallFrom == BundleConstant.LOGIN) {
                    val userDataModel = UserDataModel(
                        mUserName,
                        mMobileNo,
                        mUserId,
                        mUserType
                        , mUserImagePath
                    )
                    mAppSharedPref?.saveUserData(userDataModel)
                    if (!TextUtils.isEmpty(mCallFrom) && mCallFrom == BundleConstant.LOGIN) {

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
                        setResult(Activity.RESULT_OK)
                    }

                    finish()

                } else {
                    val intent = Intent()
                    intent.putExtra(BundleConstant.KEY_REG_USER, mUserRegistrationModel)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

    private fun userLogin(userType: String, mobileNo: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        LoginApiProvider(mApplication).userLogin(userType, mobileNo, object :
            RetroApiListener<List<LoginModel>> {
            override fun onApiSuccess(response: List<LoginModel>) {
                if (mAviLoader.isShowing) {
                    mAviLoader.dismiss()
                }
                if (!response.isNullOrEmpty()) {
                    if (AppConstant.STATUS_SUCCESS.equals(response[0].status, ignoreCase = true)) {
                        if(!isIntiView){
                            initView()
                            isIntiView=true
                        }

                        mOtp = response[0].OTP
                        mUserId = response[0].userId
                        mUserName = response[0].userName
                        mUserImagePath = response[0].imgPath
                        tvMsg.text = response[0].msg
                        startTimer()
                    } else {
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            response[0].msg!!, object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {
                                    finish()
                                }

                            }
                        )
                    }
                } else {
                    EasyDialogUtils.showOkDialog(
                        mBaseActivity,
                        getString(R.string.app_name),
                        response[0].msg!!, object : DialogOkInterface {
                            override fun doOnOkBtnClick(activity: Activity) {
                                finish()
                            }

                        }
                    )
                }
            }

            override fun onApiFailed(errorMsg: String) {
                if (mAviLoader.isShowing) {
                    mAviLoader.dismiss()
                }
                Log.e(RegistrationStep1Activity.TAG, "getCity() $errorMsg")
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    errorMsg, object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                            finish()
                        }

                    }
                )
            }
        })

    }

    private fun startTimer() {
        tvResendOtp.isEnabled = false
        tvResendOtp.setTextColor(ContextCompat.getColor(mBaseActivity, R.color.gray_text))
        mCountDowntimer = object : CountDownTimer(1000 * 90, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvResendOtp.text = "Resend OTP in : ${millisUntilFinished / 1000} Second"
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                tvResendOtp.isEnabled = true
                tvResendOtp.setTextColor(ContextCompat.getColor(mBaseActivity, R.color.dark_blue))
                tvResendOtp.text = "Resend OTP"
            }

        }
        mCountDowntimer?.start()
    }


    override fun onDestroy() {
        mCountDowntimer?.cancel()
        super.onDestroy()
    }

    private fun getOTP(emailId: String?, mobileNo: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        LoginApiProvider(mApplication).getOTP(emailId, mobileNo, object :
            RetroApiListener<List<LoginModel>> {
            override fun onApiSuccess(response: List<LoginModel>) {
                if (mAviLoader.isShowing) {
                    mAviLoader.dismiss()
                }
                if (!response.isNullOrEmpty()) {
                    if (AppConstant.STATUS_SUCCESS.equals(response[0].status, ignoreCase = true)) {
                        if(!isIntiView){
                            initView()
                            isIntiView=true
                        }
                        mOtp = response[0].OTP
                        tvMsg.text = response[0].msg
                        startTimer()
                    } else {
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            response[0].msg!!, object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {
                                    finish()
                                }

                            }
                        )
                    }
                } else {
                    EasyDialogUtils.showOkDialog(
                        mBaseActivity,
                        getString(R.string.app_name),
                        response[0].msg!!, object : DialogOkInterface {
                            override fun doOnOkBtnClick(activity: Activity) {
                                finish()
                            }

                        }
                    )
                }
            }

            override fun onApiFailed(errorMsg: String) {
                if (mAviLoader.isShowing) {
                    mAviLoader.dismiss()
                }
                Log.e(RegistrationStep1Activity.TAG, "getCity() $errorMsg")
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    errorMsg, object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                            finish()
                        }

                    }
                )
            }
        })

    }
}


