package com.pratham.dse.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.angel.accessories.provider.RegistrationApiProvider
import com.pratham.dse.R
import com.pratham.dse.adaptor.CitySpinnerAdaptor
import com.pratham.dse.adaptor.SpinnerCustomAdaptor
import com.pratham.dse.adaptor.StateSpinnerAdaptor
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.models.CityModel
import com.pratham.dse.models.StateModel
import com.pratham.dse.models.UserRegistrationModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.login.OTPActivity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.AppUtils
import com.pratham.dse.utils.BundleConstant
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.pratham.dse.adaptor.AutoCompleteStateAdapter
import kotlinx.android.synthetic.main.activity_registration_step1.*
import java.util.ArrayList

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class RegistrationStep1NewActivity : BaseActivity(), AdapterView.OnItemClickListener{
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) {
            val stateId = stateList?.get(position)?.stateId
            stateId?.let {
                getCity(it)
            }

        } else {
            cityList = mutableListOf()
            val cityModel = CityModel()
            cityModel.cityName = getString(R.string.select_city)
            cityList!!.add(cityModel)
            citySpinner.adapter = CitySpinnerAdaptor(mBaseActivity, cityList!!)
        }
    }

    private var userTypes: Array<String>? = null
    private var stateList: ArrayList<StateModel>? = null
    private var cityList: MutableList<CityModel>? = null
    private var isMobileNoVarify = false

    companion object {
        val TAG = RegistrationStep1NewActivity::class.java.simpleName
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, RegistrationStep1NewActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_registration_step1)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        getState()

    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_registration)
        atcState.threshold=1
        userTypes = resources.getStringArray(R.array.userTypes)
        userTypes?.let {
            userTypeSpinner.adapter =
                SpinnerCustomAdaptor(mBaseActivity, it)
        }
        userTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                etMobile.text = null
                isMobileNoVarify = false
            }

        }
        atcState.onItemClickListener = this
        tvNext.setOnClickListener {
            val userType = userTypeSpinner.selectedItemPosition.toString()
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val mobileNo = etMobile.text.toString().trim()
            val alternateMobile = etAlternateMobile.text.toString().trim()
            val email = etEmailId.text.toString().trim()
            val aadhaarNo = etAadhaarNo.text.toString().trim()
//            val state = stateList?.get(stateSpinner.selectedItemPosition)?.stateId
            val city = cityList?.get(citySpinner.selectedItemPosition)?.cityId
            val address = etAddress.text.toString().trim()
            val pinCode = etPinCode.text.toString().trim()
            when {
                userTypeSpinner.selectedItemPosition == 0 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_select_user),
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(firstName) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_first_name),
                        Toast.LENGTH_LONG
                    ).show()
                    etFirstName.requestFocus()
                }
                TextUtils.isEmpty(lastName) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_last_name),
                        Toast.LENGTH_LONG
                    ).show()
                    etLastName.requestFocus()
                }
                mobileNo.length < 10 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_mobile),
                        Toast.LENGTH_LONG
                    ).show()
                    etMobile.requestFocus()
                }
                !isMobileNoVarify -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_already_mobile),
                        Toast.LENGTH_LONG
                    ).show()
                    etMobile.requestFocus()
                }
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_email_id),
                        Toast.LENGTH_LONG
                    ).show()
                    etEmailId.requestFocus()
                }
                !AppUtils.isValidEmail(email) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_valid_email_id),
                        Toast.LENGTH_LONG
                    ).show()
                    etEmailId.requestFocus()
                }
                aadhaarNo.length < 12 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_aadhaar),
                        Toast.LENGTH_LONG
                    ).show()
                    etAadhaarNo.requestFocus()
                }
//                stateSpinner.selectedItemPosition == 0 -> {
//                    Toast.makeText(
//                        applicationContext,
//                        getString(R.string.msg_select_state),
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
                citySpinner.selectedItemPosition == 0 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_select_city),
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(address) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_address),
                        Toast.LENGTH_LONG
                    ).show()
                    etAddress.requestFocus()
                }
                pinCode.length < 6 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.msg_enter_pincode),
                        Toast.LENGTH_LONG
                    ).show()
                    etPinCode.requestFocus()
                }
                else -> {
                    val userRegistrationModel = UserRegistrationModel()
                    userRegistrationModel.userType = userType
                    userRegistrationModel.firstName = firstName
                    userRegistrationModel.lastName = lastName
                    userRegistrationModel.mobileNo = mobileNo
                    userRegistrationModel.alternateMobile = alternateMobile
                    userRegistrationModel.email = email
                    userRegistrationModel.aadhaarnumber = aadhaarNo
//                    userRegistrationModel.stateId = state
                    userRegistrationModel.cityId = city
                    userRegistrationModel.address = address
                    userRegistrationModel.pincode = pinCode
                    val bundle = Bundle()
                    bundle.putParcelable(
                        BundleConstant.KEY_REG_USER,
                        userRegistrationModel
                    )
                    RegistrationStep2NewActivity.startActivityForResult(
                        this@RegistrationStep1NewActivity,
                        bundle,
                        AppConstant.PHOTO_ACTIVITY_FLAG
                    )
                }
            }


        }
        etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                etMobile.setError(null)
                isMobileNoVarify = false
                val userType = userTypeSpinner.selectedItemPosition.toString()
                val mobileNo = etMobile.text.toString()
                if (userType != "0" && mobileNo.length == 10) {
                    checkMobileNumber(userType, mobileNo)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }



    private fun getState() {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
         mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity,R.color.matColor8))
        mAviLoader.show()
        RegistrationApiProvider(mApplication).getStates(object :
            RetroApiListener<ArrayList<StateModel>> {
            override fun onApiSuccess(response: ArrayList<StateModel>) {
                if(mAviLoader.isShowing){
                    mAviLoader.dismiss()
                }
                if (!response.isNullOrEmpty()) {
                    if (AppConstant.STATUS_SUCCESS.equals(response[0].status, ignoreCase = true)) {
                        response[0].stateName = getString(R.string.select_state)
                        stateList = response

                        atcState.setAdapter(AutoCompleteStateAdapter(mBaseActivity,R.layout.adp_spinner,stateList!!))

//                        stateSpinner.adapter = StateSpinnerAdaptor(mBaseActivity, stateList!!)
                    } else {

                        EasyDialogUtils.showInfoDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            response[0].msg!!
                        )
                    }
                } else {
                    EasyDialogUtils.showInfoDialog(
                        mBaseActivity,
                        getString(R.string.app_name),
                        getString(R.string.msg_null_data)
                    )
                }
            }

            override fun onApiFailed(errorMsg: String) {
                if(mAviLoader.isShowing){
                    mAviLoader.dismiss()
                }

                Log.e(TAG, "getState() $errorMsg")
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    errorMsg, object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                        }

                    }
                )
            }
        })

    }

    private fun getCity(stateId: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
         mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity,R.color.matColor8))
        mAviLoader.show()
        RegistrationApiProvider(mApplication).getCity(stateId, object :
            RetroApiListener<MutableList<CityModel>> {
            override fun onApiSuccess(response: MutableList<CityModel>) {
                if(mAviLoader.isShowing){
                    mAviLoader.dismiss()
                }
                if (!response.isNullOrEmpty()) {
                    if (AppConstant.STATUS_SUCCESS.equals(response[0].status, ignoreCase = true)) {
                        response[0].cityName = getString(R.string.select_city)
                        cityList = response
                        citySpinner.adapter = CitySpinnerAdaptor(mBaseActivity, cityList!!)
                    } else {
                        EasyDialogUtils.showInfoDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            response[0].msg!!
                        )
                    }
                } else {
                    EasyDialogUtils.showInfoDialog(
                        mBaseActivity,
                        getString(R.string.app_name),
                        getString(R.string.msg_null_data)
                    )
                }
            }

            override fun onApiFailed(errorMsg: String) {
                if(mAviLoader.isShowing){
                    mAviLoader.dismiss()
                }
                Log.e(TAG, "getCity() $errorMsg")
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    errorMsg, object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                        }

                    }
                )
            }
        })

    }

//    private fun checkMobileNumber(userRegistrationModel: UserRegistrationModel) {
//        if (!TextUtils.isEmpty(userRegistrationModel.userType)
//            && !TextUtils.isEmpty(
//                userRegistrationModel.mobileNo
//            )
//        ) {
//            mAviLoader?.show()
//            RegistrationApiProvider(mApplication).checkMobileNo(
//                userRegistrationModel.userType!!,
//                userRegistrationModel.mobileNo!!,
//                object :
//                    RetroApiListener<List<BaseResponseModel>> {
//                    override fun onApiSuccess(response: List<BaseResponseModel>) {
//                        if (!response.isNullOrEmpty()) {
//                            if (AppConstant.STATUS_SUCCESS.equals(
//                                    response[0].status,
//                                    ignoreCase = true
//                                )
//                            ) {
//                                val bundle = Bundle()
//                                bundle.putParcelable(
//                                    BundleConstant.KEY_REG_USER,
//                                    userRegistrationModel
//                                )
//                                RegistrationStep2Activity.startActivityForResult(
//                                    this@RegistrationStep1Activity,
//                                    bundle,
//                                    AppConstant.PHOTO_ACTIVITY_FLAG
//                                )
//                            } else {
//                                EasyDialogUtils.showInfoDialog(
//                                    mBaseActivity,
//                                    getString(R.string.app_name),
//                                    response[0].msg!!
//                                )
//                            }
//                        } else {
//                            EasyDialogUtils.showInfoDialog(
//                                mBaseActivity,
//                                getString(R.string.app_name),
//                                getString(R.string.msg_null_data)
//                            )
//                        }
//                        mAviLoader?.hide()
//                    }
//
//                    override fun onApiFailed(errorMsg: String) {
//                        Log.e(TAG, "checkMobileNumber() $errorMsg")
//                        mAviLoader?.hide()
//                        EasyDialogUtils.showOkDialog(
//                            mBaseActivity,
//                            getString(R.string.app_name),
//                            errorMsg, object : DialogOkInterface {
//                                override fun doOnOkBtnClick(activity: Activity) {
//                                }
//
//                            }
//                        )
//                    }
//                })
//        }
//
//    }

    private fun checkMobileNumber(userType: String?, mobileNo: String?) {
        if (!TextUtils.isEmpty(userType)
            && !TextUtils.isEmpty(
                mobileNo
            )
        ) {
            RegistrationApiProvider(mApplication).checkMobileNo(
                userType,
                mobileNo,
                object :
                    RetroApiListener<List<BaseResponseModel>> {
                    override fun onApiSuccess(response: List<BaseResponseModel>) {
                        if (!response.isNullOrEmpty()) {
                            if (!AppConstant.STATUS_SUCCESS.equals(
                                    response[0].status,
                                    ignoreCase = true
                                )
                            ) {
                                etMobile.error = response[0].msg
                            } else {
                                isMobileNoVarify = true
                            }
                        } else {
                            EasyDialogUtils.showInfoDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_null_data)
                            )
                        }
                    }

                    override fun onApiFailed(errorMsg: String) {
                        Log.e(TAG, "checkMobileNumber() $errorMsg")
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            errorMsg, object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {
                                }

                            }
                        )
                    }
                })
        }

    }


    private fun userRegistration(userRegistrationModel: UserRegistrationModel) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
         mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity,R.color.matColor8))
        mAviLoader.show()
        RegistrationApiProvider(mApplication).userRegister(
            userRegistrationModel,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_reg_done), object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {
                                        finish()
                                    }

                                }
                            )

                        } else {
                            EasyDialogUtils.showInfoDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                response[0].msg!!
                            )
                        }
                    } else {
                        EasyDialogUtils.showInfoDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            getString(R.string.msg_null_data)
                        )
                    }
                    if(mAviLoader.isShowing){
                        mAviLoader.dismiss()
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(TAG, "UserRegistration() $errorMsg")
                    if(mAviLoader.isShowing){
                        mAviLoader.dismiss()
                    }
                    EasyDialogUtils.showOkDialog(
                        mBaseActivity,
                        getString(R.string.app_name),
                        errorMsg, object : DialogOkInterface {
                            override fun doOnOkBtnClick(activity: Activity) {
                            }

                        }
                    )
                }
            })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == AppConstant.PHOTO_ACTIVITY_FLAG && resultCode == Activity.RESULT_OK -> {
                data?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(
                        BundleConstant.KEY_REG_USER,
                        it.getParcelableExtra(BundleConstant.KEY_REG_USER)
                    )
                    RegistrationStep3NewActivity.startActivityForResult(
                        this@RegistrationStep1NewActivity,
                        bundle,
                        AppConstant.AADHAAR_ACTIVITY_FLAG
                    )

                }
            }
            requestCode == AppConstant.AADHAAR_ACTIVITY_FLAG && resultCode == Activity.RESULT_OK -> {
                data?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(
                        BundleConstant.KEY_REG_USER,
                        it.getParcelableExtra(BundleConstant.KEY_REG_USER)
                    )
                    bundle.putString(BundleConstant.KEY_FROM, BundleConstant.REGISTRATION)
                    OTPActivity.startActivityForResult(
                        this@RegistrationStep1NewActivity,
                        bundle,
                        AppConstant.OTP_ACTIVITY_FLAG
                    )
                }
            }
            requestCode == AppConstant.OTP_ACTIVITY_FLAG && resultCode == Activity.RESULT_OK -> {
                data?.let {
                    val userRegistrationModel =
                        it.getParcelableExtra(BundleConstant.KEY_REG_USER) as UserRegistrationModel
                    userRegistration(userRegistrationModel)
                }
            }
        }
    }
}
