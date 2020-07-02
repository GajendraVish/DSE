package com.pratham.dse.ui.Techs.exam_schedule.examination

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.accessories.provider.TechApiProvider
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.CommonUtils
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.pratham.dse.R
import com.pratham.dse.adaptor.ExamCenterSpinnerAdaptor
import com.pratham.dse.adaptor.ExamShiftAdapter
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.models.ExamCenterModel
import com.pratham.dse.models.ExamStatusDataModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.pratham.dse.utils.Utils
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_exam_shift.*
import java.util.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ExamShiftActivity : BaseActivity(), ExamShiftAdapter.OnExamShiftClickListener {

    private val mExamCenterList = mutableListOf<ExamCenterModel>()
    private val mShiftList = mutableListOf<String>()
    private var mExamId: String? = null
    private var mShiftId: String? = null
    private var mLocation: Location? = null
    private var mExamStatusDataModel: ExamStatusDataModel? = null

    companion object {
        private val TAG = ExamShiftActivity::class.java.simpleName
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ExamShiftActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_exam_shift)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        readDataFromBundle()


    }

    override fun onResume() {
        super.onResume()
        checkAndGetLocation()
    }


    private fun readDataFromBundle() {
        mExamId = intent.getStringExtra(BundleConstant.KEY_EXAM_ID)
        mShiftId = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ID)
        mExamId?.let {
            getExamCenter(it)
        }
        val shiftOneStartTime = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ONE_START_TIME)
        val shiftTwoStartTime = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ONE_TWO_TIME)
        val shiftThreeStartTime =
            intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ONE_THREE_TIME)
        val shiftFourStartTime = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ONE_FOUR_TIME)
        setShiftListData(
            shiftOneStartTime,
            shiftTwoStartTime,
            shiftThreeStartTime,
            shiftFourStartTime
        )

    }

    private fun setShiftListData(
        shiftOneStartTime: String?,
        shiftTwoStartTime: String?,
        shiftThreeStartTime: String?,
        shiftFourStartTime: String?
    ) {
        mShiftList.clear()
        if (!TextUtils.isEmpty(shiftOneStartTime)) {
            mShiftList.add(getString(R.string.shift_one))
        }
        if (!TextUtils.isEmpty(shiftTwoStartTime)) {
            mShiftList.add(getString(R.string.shift_two))
        }
        if (!TextUtils.isEmpty(shiftThreeStartTime)) {
            mShiftList.add(getString(R.string.shift_three))
        }
        if (!TextUtils.isEmpty(shiftFourStartTime)) {
            mShiftList.add(getString(R.string.shift_four))
        }
        val mAdapter = ExamShiftAdapter(this, this, mShiftList)
        rvItemList.adapter = mAdapter
    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_exam_shift)
        val examCenter = ExamCenterModel()
        examCenter.centerName = getString(R.string.select_center)
        mExamCenterList.add(examCenter)
        examCenterSpinner.adapter =
            ExamCenterSpinnerAdaptor(mBaseActivity, mExamCenterList)
        // set up the RecyclerView
        rvItemList.layoutManager = LinearLayoutManager(this)



        etDepartureTime.setOnClickListener {
            checkAndOpenTimePicker(etDepartureTime, AppConstant.FLAG_DEPARTURE)
        }
        etArrivalTime.setOnClickListener {
            if (TextUtils.isEmpty(etDepartureTime.text)) {
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_please_submit_departure_time_before_arrival), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {

                        }

                    }
                )
            } else {
                checkAndOpenTimePicker(etArrivalTime, AppConstant.FLAG_ARRIVAL)
            }

        }
        examCenterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                etArrivalTime.setText("")
                etArrivalTime.isEnabled = true
                etArrivalTime.setBackgroundResource(R.drawable.round_rect_grey)
                etDepartureTime.setText("")
                etDepartureTime.isEnabled = true
                etDepartureTime.setBackgroundResource(R.drawable.round_rect_grey)
                if (position > 0) {
                    val ecmId = mExamCenterList[position].ecmId
                    if (!TextUtils.isEmpty(mShiftId) && !TextUtils.isEmpty(ecmId)) {
                        getExamStatusData(mShiftId!!, ecmId!!)
                    }
                }

            }

        }


    }

    private fun checkAndOpenTimePicker(etTime: EditText, flag: String) {
        when {
            mLocation == null -> {
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_location_not_found), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                            checkAndGetLocation()
                        }

                    }
                )
            }
            examCenterSpinner.selectedItemPosition == 0 -> {
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.please_select_center), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {

                        }

                    }
                )

            }

            else -> {

                val time = CommonUtils.getDateWithPattern(Date(), "HH:mm")
                val ecmID = mExamCenterList[examCenterSpinner.selectedItemPosition].ecmId
                val latitude = mLocation?.latitude.toString()
                val longtitude = mLocation?.longitude.toString()
                when (flag) {
                    AppConstant.FLAG_ARRIVAL -> {
                        saveArrivalTime(etTime, ecmID, mShiftId, latitude, longtitude, time)
                    }
                    AppConstant.FLAG_DEPARTURE -> {
                        saveDepatureTime(etTime, ecmID, mShiftId, latitude, longtitude, time)
                    }
                }

//                val timePickerFragment = TimePickerFragment()
//                timePickerFragment.setOnTimePicked { hour, minute ->
//
//                }
//                timePickerFragment.show(supportFragmentManager, "TimePicker")
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExamShiftClick(shiftName: String) {

        when {
            examCenterSpinner.selectedItemPosition == 0 -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_select_center),
                    Toast.LENGTH_LONG
                ).show()
            }
            TextUtils.isEmpty(etDepartureTime.text)->{
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_please_submit_departure_time), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {

                        }

                    }
                )
            }
            TextUtils.isEmpty(etArrivalTime.text)->{
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_please_submit_arrival_time), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {

                        }

                    }
                )
            }
            else -> {

                val bundle = Bundle()
                bundle.putString(
                    BundleConstant.KEY_EXAM_CENTER_ID,
                    mExamCenterList[examCenterSpinner.selectedItemPosition].ecmId
                )
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ID, mShiftId)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_NAME, shiftName)
                bundle.putParcelable(BundleConstant.KEY_EXAM_STATUS, mExamStatusDataModel)
                ExamShiftDetailsActivity.startActivityForResult(
                    mBaseActivity,
                    bundle,
                    AppConstant.SHIFT_DETAILS_ACTIVITY_FLAG
                )
            }
        }

    }

    private fun getExamCenter(examId: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).getExamCenter(
            userDataModel?.userId,
            userDataModel?.userMobile,
            examId,
            object :
                RetroApiListener<MutableList<ExamCenterModel>> {
                override fun onApiSuccess(response: MutableList<ExamCenterModel>) {
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            response[0].centerName = getString(R.string.select_center)
                            mExamCenterList.clear()
                            mExamCenterList.addAll(response)
                            examCenterSpinner.adapter =
                                ExamCenterSpinnerAdaptor(mBaseActivity, mExamCenterList)
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
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    Log.e(RegistrationStep1Activity.TAG, "getState() $errorMsg")
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

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this@ExamShiftActivity,
            arrayOf(permission),
            requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == AppConstant.PERMISSION_REQUEST_CODE_LOCATION) {

            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkAndGetLocation()
            }

        }
    }

    private fun checkAndGetLocation() {
        if (Utils.checkPermission(
                this@ExamShiftActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
//            if (SmartLocation.with(mBaseActivity).location().state().isGpsAvailable) {
            SmartLocation.with(this@ExamShiftActivity).location()
                .oneFix()
                .start {
                    mLocation = it
                }
//            } else {
//                EasyDialogUtils.showOkDialog(
//                    mBaseActivity,
//                    getString(R.string.app_name),
//                    getString(R.string.msg_please_enable_gps), object : DialogOkInterface {
//                        override fun doOnOkBtnClick(activity: Activity) {
//                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                        }
//
//                    }
//                )
//            }
        } else {
            requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                AppConstant.PERMISSION_REQUEST_CODE_LOCATION
            )
        }
    }

    private fun saveDepatureTime(
        edtitext: EditText,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        departuretime: String?
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).saveDepartureTime(
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            departuretime,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            edtitext.setText(departuretime)
                            edtitext.isEnabled = false
                            edtitext.setBackgroundResource(R.drawable.round_rect_grey_disable)
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_save_departuretime),
                                object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {

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
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(TAG, "UserRegistration() $errorMsg")
                    if (mAviLoader.isShowing) {
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

    private fun saveArrivalTime(
        edtitext: EditText,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        arrivaltime: String?
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).saveArrivalTime(
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            arrivaltime,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            edtitext.setText(arrivaltime)
                            edtitext.isEnabled = false
                            edtitext.setBackgroundResource(R.drawable.round_rect_grey_disable)
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_save_arrival),
                                object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {

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
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(TAG, "UserRegistration() $errorMsg")
                    if (mAviLoader.isShowing) {
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

    private fun getExamStatusData(shiftId: String, ecmId: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).getExamStatus(
            userDataModel?.userId,
            shiftId, ecmId,
            object :
                RetroApiListener<List<ExamStatusDataModel>> {
                override fun onApiSuccess(response: List<ExamStatusDataModel>) {
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            if (response.size > 1) {
                                setExamData(response[1])
                            }

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
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    Log.e(RegistrationStep1Activity.TAG, "getInstallationData() $errorMsg")
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

    private fun setExamData(examStatusDataModel: ExamStatusDataModel?) {
        examStatusDataModel?.let {
            mExamStatusDataModel = it
            if (!TextUtils.isEmpty(it.departure_time)) {
                etDepartureTime.setText(it.departure_time)
                etDepartureTime.isEnabled = false
                etDepartureTime.setBackgroundResource(R.drawable.round_rect_grey_disable)
            }

            if (!TextUtils.isEmpty(it.arrival_time)) {
                etArrivalTime.setText(it.arrival_time)
                etArrivalTime.isEnabled = false
                etArrivalTime.setBackgroundResource(R.drawable.round_rect_grey_disable)
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.SHIFT_DETAILS_ACTIVITY_FLAG) {
            val ecmId = mExamCenterList[examCenterSpinner.selectedItemPosition].ecmId
            if (!TextUtils.isEmpty(mShiftId) && !TextUtils.isEmpty(ecmId)) {
                getExamStatusData(mShiftId!!, ecmId!!)
            }
        }
    }


}
