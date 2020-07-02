package com.pratham.dse.ui.Techs.exam_schedule.examination

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.angel.accessories.provider.RegistrationApiProvider
import com.angel.accessories.provider.TechApiProvider
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TimePickerFragment
import com.bhaskar.utils.TransAviLoader
import com.imageprovider.imageCompression.ImageCompression
import com.imageprovider.imageCompression.ImageCompressionListener
import com.pratham.dse.R
import com.pratham.dse.RemarkIssueDialog
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.models.ExamStatusDataModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.pratham.dse.utils.ImageUtil
import com.pratham.dse.utils.Utils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_exam_shift_details.*
import java.io.File

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ExamShiftDetailsActivity : BaseActivity() {
    private var mEcmID: String? = null
    private var mShiftId: String? = null
    private var mShiftName: String? = null
    private var mLocation: Location? = null
    private var mExamStatusDataModel: ExamStatusDataModel? = null

    companion object {
        private val TAG = ExamShiftDetailsActivity::class.java.simpleName
        fun startActivityForResult(context: Activity, bundle: Bundle?, flag: Int) {
            val intent = Intent(context, ExamShiftDetailsActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivityForResult(intent, flag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_exam_shift_details)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        readDataFromBundle()


    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_exam_shift_details)
        etOnTime.setOnClickListener {
            when (mShiftName) {
                AppConstant.SHIFT_1 -> {
                    checkAndOpenTimePicker(etOnTime, AppConstant.URL_ON_TIME_SHIFT_1)
                }
                AppConstant.SHIFT_2 -> {
                    checkAndOpenTimePicker(etOnTime, AppConstant.URL_ON_TIME_SHIFT_2)
                }
                AppConstant.SHIFT_3 -> {
                    checkAndOpenTimePicker(etOnTime, AppConstant.URL_ON_TIME_SHIFT_3)

                }
                AppConstant.SHIFT_4 -> {
                    checkAndOpenTimePicker(etOnTime, AppConstant.URL_ON_TIME_SHIFT_4)

                }

            }

        }
        etOffTime.setOnClickListener {
            when (mShiftName) {
                AppConstant.SHIFT_1 -> {
                    checkAndOpenTimePicker(etOffTime, AppConstant.URL_OFF_TIME_SHIFT_1)
                }
                AppConstant.SHIFT_2 -> {
                    checkAndOpenTimePicker(etOffTime, AppConstant.URL_OFF_TIME_SHIFT_2)
                }
                AppConstant.SHIFT_3 -> {
                    checkAndOpenTimePicker(etOffTime, AppConstant.URL_OFF_TIME_SHIFT_3)

                }
                AppConstant.SHIFT_4 -> {
                    checkAndOpenTimePicker(etOffTime, AppConstant.URL_OFF_TIME_SHIFT_4)

                }

            }

        }
        tvBtnAttandance.setOnClickListener {
            when (mShiftName) {
                AppConstant.SHIFT_1 -> {
                    checkAndUpdateAttandance(AppConstant.URL_MID_ATTANDANCE_SHIFT_1)
                }
                AppConstant.SHIFT_2 -> {
                    checkAndUpdateAttandance(AppConstant.URL_MID_ATTANDANCE_SHIFT_2)
                }
                AppConstant.SHIFT_3 -> {
                    checkAndUpdateAttandance(AppConstant.URL_MID_ATTANDANCE_SHIFT_3)

                }
                AppConstant.SHIFT_4 -> {
                    checkAndUpdateAttandance(AppConstant.URL_MID_ATTANDANCE_SHIFT_4)

                }

            }

        }
        tvBtnAttachment.setOnClickListener {
            checkAndOpenCamera()
        }
        etMarkIssue.setOnClickListener {
            if (mLocation == null) {
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_location_not_found), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                            checkAndGetLocation()
                        }

                    }
                )
            } else {
                val issueDialog =
                    RemarkIssueDialog(mBaseActivity,
                        etMarkIssue.text.toString().trim(),
                        mShiftName,
                        mEcmID,
                        mLocation?.latitude.toString() ?: "",
                        mLocation?.longitude.toString() ?: "",
                        mShiftId,
                        object : RemarkIssueDialog.RemarkUpdateListener {
                            override fun onRemarkUpdateSuccess(remark: String?) {
                                etMarkIssue.setText(remark)
                            }

                        }
                    )
                issueDialog.show()
            }


        }

    }

    private fun checkAndOpenTimePicker(
        etTime: EditText, fullUrl: String
    ) {
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
            else -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.setOnTimePicked { hour, minute ->
                    val time = "$hour:$minute"
                    val latitude = mLocation?.latitude.toString()
                    val longtitude = mLocation?.longitude.toString()
                    updateShiftTime(
                        fullUrl,
                        mEcmID,
                        mShiftId,
                        latitude,
                        longtitude,
                        time,
                        etTime
                    )

                }
                timePickerFragment.show(supportFragmentManager, "TimePicker")
            }
        }

    }

    private fun checkAndUpdateAttandance(
        fullUrl: String
    ) {
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
            else -> {
                val attandance = AppConstant.PRESENT
                val latitude = mLocation?.latitude.toString()
                val longtitude = mLocation?.longitude.toString()
                updateAttandance(
                    fullUrl,
                    mEcmID,
                    mShiftId,
                    latitude,
                    longtitude,
                    attandance
                )
            }
        }

    }

    override fun onResume() {
        super.onResume()
        checkAndGetLocation()
    }

    private fun checkAndGetLocation() {
        if (Utils.checkPermission(
                this@ExamShiftDetailsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
//            if (SmartLocation.with(mBaseActivity).location().state().isGpsAvailable) {
            SmartLocation.with(this@ExamShiftDetailsActivity).location()
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

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this@ExamShiftDetailsActivity,
            arrayOf(permission),
            requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == AppConstant.PERMISSION_REQUEST_CODE_STORAGE || requestCode == AppConstant.PERMISSION_REQUEST_CODE_CAMERA) {

            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkAndOpenCamera()
            }

        } else if (requestCode == AppConstant.PERMISSION_REQUEST_CODE_LOCATION) {

            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkAndGetLocation()
            }

        }
    }

    private fun readDataFromBundle() {

        mEcmID = intent.getStringExtra(BundleConstant.KEY_EXAM_CENTER_ID)
        mShiftId = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_ID)
        mShiftName = intent.getStringExtra(BundleConstant.KEY_EXAM_SHIFT_NAME)
        mExamStatusDataModel = intent.getParcelableExtra(BundleConstant.KEY_EXAM_STATUS)
        mExamStatusDataModel?.let {
            when (mShiftName) {
                AppConstant.SHIFT_1 -> {
                    etOnTime.setText(it.shift_one_start_time ?: "")
                    etOffTime.setText(it.shift_one_end_time ?: "")
                    etMarkIssue.setText(it.tyeassd_shift_one_remark ?: "")
                    val imagePath = AppConstant.IMAGE_BASE_URL + it.tyeassd_shift_one_upload
                    if (!TextUtils.isEmpty(imagePath)) {
                        ImageUtil.loadImage(imagePath, ivUploads, R.mipmap.ic_no_image)

                    }
                    if (!TextUtils.isEmpty(it.shift_one_mid_attendance)) {
                        tvBtnAttandance.isEnabled = false
                        tvBtnAttandance.setBackgroundResource(R.drawable.round_rect_grey_disable)
                    }

                }
                AppConstant.SHIFT_2 -> {
                    etOnTime.setText(it.shift_two_start_time ?: "")
                    etOffTime.setText(it.shift_two_end_time ?: "")
                    etMarkIssue.setText(it.tyeassd_shift_two_remark ?: "")
                    val imagePath = AppConstant.IMAGE_BASE_URL + it.tyeassd_shift_two_upload
                    if (!TextUtils.isEmpty(imagePath)) {
                        ImageUtil.loadImage(imagePath, ivUploads, R.mipmap.ic_no_image)

                    }
                    if (!TextUtils.isEmpty(it.shift_two_mid_attendance)) {
                        tvBtnAttandance.isEnabled = false
                        tvBtnAttandance.setBackgroundResource(R.drawable.round_rect_grey_disable)
                    }
                }
                AppConstant.SHIFT_3 -> {
                    etOnTime.setText(it.shift_three_start_time ?: "")
                    etOffTime.setText(it.shift_three_end_time ?: "")
                    etMarkIssue.setText(it.tyeassd_shift_three_remark ?: "")
                    val imagePath = AppConstant.IMAGE_BASE_URL + it.tyeassd_shift_three_upload
                    if (!TextUtils.isEmpty(imagePath)) {
                        ImageUtil.loadImage(imagePath, ivUploads, R.mipmap.ic_no_image)

                    }
                    if (!TextUtils.isEmpty(it.shift_three_mid_attendance)) {
                        tvBtnAttandance.isEnabled = false
                        tvBtnAttandance.setBackgroundResource(R.drawable.round_rect_grey_disable)
                    }
                }
                AppConstant.SHIFT_4 -> {
                    etOnTime.setText(it.shift_four_start_time ?: "")
                    etOffTime.setText(it.shift_four_end_time ?: "")
                    etMarkIssue.setText(it.tyeassd_shift_four_remark ?: "")
                    val imagePath = AppConstant.IMAGE_BASE_URL + it.tyeassd_shift_four_upload
                    if (!TextUtils.isEmpty(imagePath)) {
                        ImageUtil.loadImage(imagePath, ivUploads, R.mipmap.ic_no_image)

                    }
                    if (!TextUtils.isEmpty(it.shift_four_mid_attendance)) {
                        tvBtnAttandance.isEnabled = false
                        tvBtnAttandance.setBackgroundResource(R.drawable.round_rect_grey_disable)
                    }

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

    private fun checkAndOpenCamera() {
        if (Utils.checkPermission(
                this@ExamShiftDetailsActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            if (Utils.checkPermission(
                    this@ExamShiftDetailsActivity,
                    Manifest.permission.CAMERA
                )
            ) {
                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this@ExamShiftDetailsActivity);
            } else {
                requestPermission(
                    Manifest.permission.CAMERA,
                    AppConstant.PERMISSION_REQUEST_CODE_CAMERA
                )
            }


        } else {
            requestPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                AppConstant.PERMISSION_REQUEST_CODE_STORAGE
            )
        }
    }

    private fun updateShiftTime(
        fullUrl: String,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        time: String?,
        etTime: EditText
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).updateShiftTime(
            fullUrl,
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            time,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            etTime.setText(time)
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_save_time),
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

    private fun updateAttandance(
        fullUrl: String,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        attandance: String?
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).updateAttancance(
            fullUrl,
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            attandance,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            tvBtnAttandance.isEnabled = false
                            tvBtnAttandance.setBackgroundResource(R.drawable.round_rect_grey_disable)
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_save_attendance),
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
                    Log.e(TAG, "updateAttandance() $errorMsg")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                val imgPath = result.uri.path;
                if (!TextUtils.isEmpty(imgPath)) {
                    val mAviLoader = TransAviLoader(mBaseActivity, true)
                    mAviLoader.setLoaderColor(
                        ContextCompat.getColor(
                            mBaseActivity,
                            R.color.matColor8
                        )
                    )
                    ImageCompression(
                        mBaseActivity,
                        imgPath,
                        object : ImageCompressionListener {
                            override fun onStart() {
                                mAviLoader.show()
                            }

                            override fun onCompressed(filePath: String?) {
                                if (mAviLoader.isShowing) {
                                    mAviLoader.dismiss()
                                }
                                filePath?.let {
                                    uploadPhoto(File(filePath).name, it)
                                }


                            }

                        }).execute()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                EasyDialogUtils.showInfoDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    result.error.message ?: "Error in Image"
                )
            }
        }

    }

    private fun uploadPhoto(imageName: String, imagePath: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val base64Str = ImageUtil.getFileToBase64(imagePath)
        RegistrationApiProvider(mApplication).uploadDocument(
            base64Str,
            imageName,
            object :
                RetroApiListener<List<BaseResponseModel>> {
                override fun onApiSuccess(response: List<BaseResponseModel>) {
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            updateAttachment(imageName)
                        } else {
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                response[0].msg ?: "", object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {
                                    }

                                }
                            )

                        }
                    } else {
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            getString(R.string.msg_null_data), object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {

                                }

                            }
                        )


                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(RegistrationStep1Activity.TAG, "uploadProfilePhoto() $errorMsg")
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

    private fun updateAttachment(imageName: String) {
        val latitude = mLocation?.latitude.toString()
        val longtitude = mLocation?.longitude.toString()
        when (mShiftName) {
            AppConstant.SHIFT_1 -> {
                updateAttachmentOnServer(
                    AppConstant.URL_UPLOAD_FILE_SHIFT_1,
                    mEcmID,
                    mShiftId,
                    latitude,
                    longtitude,
                    imageName
                )
            }
            AppConstant.SHIFT_2 -> {
                updateAttachmentOnServer(
                    AppConstant.URL_UPLOAD_FILE_SHIFT_2,
                    mEcmID,
                    mShiftId,
                    latitude,
                    longtitude,
                    imageName
                )
            }
            AppConstant.SHIFT_3 -> {
                updateAttachmentOnServer(
                    AppConstant.URL_UPLOAD_FILE_SHIFT_3,
                    mEcmID,
                    mShiftId,
                    latitude,
                    longtitude,
                    imageName
                )

            }
            AppConstant.SHIFT_4 -> {
                updateAttachmentOnServer(
                    AppConstant.URL_UPLOAD_FILE_SHIFT_4,
                    mEcmID,
                    mShiftId,
                    latitude,
                    longtitude,
                    imageName
                )

            }

        }
    }

    private fun updateAttachmentOnServer(
        fullUrl: String,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        fileName: String?
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).updateUploads(
            fullUrl,
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            fileName,
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
                                getString(R.string.msg_save_uploads),
                                object : DialogOkInterface {
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
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(TAG, "updateAttachment() $errorMsg")
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


}
