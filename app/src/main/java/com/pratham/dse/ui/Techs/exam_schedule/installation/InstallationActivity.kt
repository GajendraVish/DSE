package com.pratham.dse.ui.Techs.exam_schedule.installation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.angel.accessories.provider.RegistrationApiProvider
import com.angel.accessories.provider.TechApiProvider
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.*
import com.imageprovider.imageCompression.ImageCompression
import com.imageprovider.imageCompression.ImageCompressionListener
import com.pratham.dse.R
import com.pratham.dse.adaptor.ExamCenterSpinnerAdaptor
import com.pratham.dse.adaptor.JammerPhotoAdapter
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.models.ExamCenterModel
import com.pratham.dse.models.InstallationDataModel
import com.pratham.dse.models.JammerPhotoModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.pratham.dse.utils.ImageUtil
import com.pratham.dse.utils.Utils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_installation.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class InstallationActivity : BaseActivity(), JammerPhotoAdapter.OnJammerItemClickListener {

    private var mJammerPhotoModel: JammerPhotoModel? = null
    private var mJammerPhotoList = mutableListOf<JammerPhotoModel>()
    private var mExamCenterList = mutableListOf<ExamCenterModel>()
    private var mJammerPhotoAdapter: JammerPhotoAdapter? = null
    private var mExamId: String? = null
    private var mLocation: Location? = null
    private var mInstallationId: String? = null


    companion object {
        private val TAG = InstallationActivity::class.java.simpleName
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, InstallationActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAndGetLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_installation)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initUI()
        etBigInstallJammer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addJammerPhotoGrid()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        etSmallInstallJammer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addJammerPhotoGrid()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        examCenterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                clearData()
                if (position > 0) {
                    val ecmId = mExamCenterList[examCenterSpinner.selectedItemPosition].ecmId
                    ecmId?.let {
                        getInstallationData(it)
                    }

                }
            }

        }
        etDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment(mBaseActivity)
            val bundle = Bundle()
            bundle.putString(
                "key_date_format",
                CommonUtils.getDateWithPattern(Date(), AppConstant.DATE_FORMAT_YYYY_MM_DD)
            )
            datePickerFragment.arguments = bundle
            datePickerFragment.setOnDatePicked { day, month, year -> etDate.setText("$day/$month/$year") }
            datePickerFragment.show(supportFragmentManager, "Date Picker")
        }
        etTime.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.setOnTimePicked { hour, minute ->
                etTime.setText("$hour:$minute")
            }
            timePickerFragment.show(supportFragmentManager, "TimePicker")
        }
        btnSubmit.setOnClickListener {
            val ecId = mExamCenterList.get(examCenterSpinner.selectedItemPosition).ecId
            val ecmId = mExamCenterList.get(examCenterSpinner.selectedItemPosition).ecmId
            val date = CommonUtils.changeDatetimeFormat(
                etDate.text.toString().trim(),
                AppConstant.DATE_FORMAT_DD_MM_YYYY,
                AppConstant.DATE_FORMAT_YYYY_MM_DD
            )
            val time = etTime.text.toString().trim()
            val btsTower = etBtsTower.text.toString().trim()
            val btsTowerRoof = etBtsTowerRoof.text.toString().trim()
            val bigInstallJammer = etBigInstallJammer.text.toString().trim()
            val smallInstallJammer = etSmallInstallJammer.text.toString().trim()
            val reserveBigJammer = etReserveBigJammer.text.toString().trim()
            val reserveSmallJammer = etReserveSmallJammer.text.toString().trim()
            val rooms = etRooms.text.toString().trim()
            val labs = etLabs.text.toString().trim()
            val jio = etJio.text.toString().trim()
            val airtel = etAirtel.text.toString().trim()
            val idea = etIdea.text.toString().trim()
            val other = etOther.text.toString().trim()


            val isBluetooth = if (rbYesBluetooth.isChecked) {
                "1"
            } else {
                "0"
            }
            val isJio3g = if (rbJio3gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isJio4g = if (rbJio4gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isAirtel3g = if (rbAirtel3gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isAirtel4g = if (rbAirtel4gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isIdea3g = if (rbIdea3gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isIdea4g = if (rbIdea4gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isOther3g = if (rbOther3gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isOther4g = if (rbOther4gYes.isChecked) {
                "1"
            } else {
                "0"
            }
            val isSignOff = if (rbYesSignoff.isChecked) {
                "1"
            } else {
                "0"
            }
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
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_select_center),
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(date) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_select_date),
                        Toast.LENGTH_LONG
                    ).show()
                    etDate.requestFocus()
                }
                TextUtils.isEmpty(time) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_select_time),
                        Toast.LENGTH_LONG
                    ).show()
                    etTime.requestFocus()
                }
                TextUtils.isEmpty(btsTower) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_bts_tower_100_mtr),
                        Toast.LENGTH_LONG
                    ).show()
                    etBtsTower.requestFocus()
                }

                TextUtils.isEmpty(btsTowerRoof) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_bts_tower_roof),
                        Toast.LENGTH_LONG
                    ).show()
                    etBtsTowerRoof.requestFocus()
                }

                TextUtils.isEmpty(bigInstallJammer) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_install_jammer_big),
                        Toast.LENGTH_LONG
                    ).show()
                    etBigInstallJammer.requestFocus()
                }
                TextUtils.isEmpty(smallInstallJammer) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_install_jammer_small),
                        Toast.LENGTH_LONG
                    ).show()
                    etSmallInstallJammer.requestFocus()
                }


                TextUtils.isEmpty(reserveBigJammer) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_reserve_jammer_big),
                        Toast.LENGTH_LONG
                    ).show()
                    etReserveBigJammer.requestFocus()
                }
                TextUtils.isEmpty(reserveSmallJammer) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_reserve_jammer_small),
                        Toast.LENGTH_LONG
                    ).show()
                    etReserveSmallJammer.requestFocus()
                }
                TextUtils.isEmpty(rooms) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_total_number_room),
                        Toast.LENGTH_LONG
                    ).show()
                    etRooms.requestFocus()
                }
                TextUtils.isEmpty(labs) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_total_number_labs),
                        Toast.LENGTH_LONG
                    ).show()
                    etLabs.requestFocus()
                }
                TextUtils.isEmpty(jio) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_jio_signal_strength),
                        Toast.LENGTH_LONG
                    ).show()
                    etJio.requestFocus()
                }
                TextUtils.isEmpty(airtel) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_airtel_signal_strength),
                        Toast.LENGTH_LONG
                    ).show()
                    etAirtel.requestFocus()
                }
                TextUtils.isEmpty(idea) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_idea_signal_strength),
                        Toast.LENGTH_LONG
                    ).show()
                    etIdea.requestFocus()
                }
                TextUtils.isEmpty(other) -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_other_signal_strength),
                        Toast.LENGTH_LONG
                    ).show()
                    etOther.requestFocus()
                }
                else -> {
                    val imagList = JSONArray()
                    for (jammer in mJammerPhotoList) {
                        if (!TextUtils.isEmpty(jammer.jammerImageName)) {
                            val imgObj = JSONObject()
                            imgObj.put("img", jammer.jammerImageName!!)
                            imagList.put(imgObj)
                        }
                    }
                    val uploads = imagList.toString()
                    val latitude = mLocation?.latitude.toString()
                    val longtitude = mLocation?.longitude.toString()
                    val instalattionId = mInstallationId ?: " "
                    val userid = mAppSharedPref?.getUserData()?.userId
                    val mobile = mAppSharedPref?.getUserData()?.userMobile

                    saveInstallationData(
                        userid, mobile, instalattionId, mExamId, ecId,
                        ecmId, btsTower, btsTowerRoof, bigInstallJammer,
                        smallInstallJammer, reserveBigJammer, reserveSmallJammer
                        , jio, airtel, idea, other, isBluetooth, isJio3g, isJio4g,
                        isAirtel3g, isAirtel4g, isIdea3g, isIdea4g, isOther3g,
                        isOther4g, isSignOff, latitude, longtitude, rooms, labs, date, time, uploads
                    )


                }


            }
        }
        readDataFromBundle()


    }

    private fun readDataFromBundle() {
        mExamId = intent.getStringExtra(BundleConstant.KEY_EXAM_ID)
        mExamId?.let {
            getExamCenter(it)
        }
    }

    private fun addJammerPhotoGrid() {
        try {
            mJammerPhotoList.clear()
            val bigTower = Integer.parseInt(etBigInstallJammer.text.toString())
            val smallTower = Integer.parseInt(etSmallInstallJammer.text.toString())
            val totalJammer = (bigTower + smallTower)
            if (totalJammer <= 20) {
                for (index in 0 until totalJammer) {
                    mJammerPhotoList.add(JammerPhotoModel())
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "addJammerPhotoGrid() ${e.message}")
        } finally {
            mJammerPhotoAdapter?.notifyDataSetChanged()
        }
    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_installation)
        val examCenter = ExamCenterModel()
        examCenter.centerName = getString(R.string.select_center)
        mExamCenterList.add(examCenter)
        examCenterSpinner.adapter =
            ExamCenterSpinnerAdaptor(mBaseActivity, mExamCenterList)
        rvJammerPhoto.layoutManager = GridLayoutManager(mBaseActivity, 3)
        mJammerPhotoAdapter =
            JammerPhotoAdapter(mBaseActivity, this@InstallationActivity, mJammerPhotoList)
        rvJammerPhoto.adapter = mJammerPhotoAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onJammerItemClick(jammer: JammerPhotoModel) {
        this.mJammerPhotoModel = jammer
        checkAndOpenCamera()
    }

    private fun checkAndOpenCamera() {
        if (Utils.checkPermission(
                this@InstallationActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            if (Utils.checkPermission(
                    this@InstallationActivity,
                    Manifest.permission.CAMERA
                )
            ) {
                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this@InstallationActivity);
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

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this@InstallationActivity,
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
                                mAviLoader?.show()
                            }

                            override fun onCompressed(filePath: String?) {
                                if (mAviLoader.isShowing) {
                                    mAviLoader.dismiss()
                                }
                                filePath?.let {
                                    mJammerPhotoModel?.jammerImageName = File(filePath).name
                                    mJammerPhotoModel?.jamerImagePath = filePath
                                    ImageUtil.loadImage(
                                        filePath,
                                        mJammerPhotoModel?.ivJammmer,
                                        R.mipmap.ic_no_image
                                    )
                                    uploadPhoto(mJammerPhotoModel)
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


    private fun uploadPhoto(jammer: JammerPhotoModel?) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val base64Str = ImageUtil.getFileToBase64(jammer?.jamerImagePath)
        RegistrationApiProvider(mApplication).uploadDocument(
            base64Str,
            jammer?.jammerImageName!!,
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
                            /*EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                getString(R.string.msg_image_uploaded), object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {
                                    }

                                }
                            )*/
                        } else {
                            mJammerPhotoModel?.ivJammmer?.setImageResource(R.mipmap.error_img)
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
                        mJammerPhotoModel?.ivJammmer?.setImageResource(R.mipmap.error_img)
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            getString(R.string.msg_null_data), object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {
                                    mJammerPhotoModel?.jamerImagePath = ""
                                    mJammerPhotoModel?.jammerImageName = ""
                                }

                            }
                        )


                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    mJammerPhotoModel?.ivJammmer?.setImageResource(R.mipmap.error_img)
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

    private fun checkAndGetLocation() {
        // Check if the location services are enabled


        if (Utils.checkPermission(
                this@InstallationActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
//            if (SmartLocation.with(mBaseActivity).location().state().isGpsAvailable) {
            SmartLocation.with(this@InstallationActivity).location()
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

    private fun saveInstallationData(
        userid: String?,
        mobile: String?,
        instalattionId: String?,
        mExamId: String?,
        ecId: String?,
        ecmId: String?,
        btsTower: String,
        btsTowerRoof: String?,
        bigInstallJammer: String?,
        smallInstallJammer: String?,
        reserveBigJammer: String?,
        reserveSmallJammer: String?,
        jio: String?,
        airtel: String?,
        idea: String?,
        other: String?,
        bluetooth: String?,
        jio3g: String?,
        jio4g: String?,
        airtel3g: String?,
        airtel4g: String?,
        idea3g: String?,
        idea4g: String?,
        other3g: String?,
        other4g: String?,
        signOff: String?,
        latitude: String?,
        longtitude: String?,
        rooms: String?,
        labs: String?,
        date: String?,
        time: String?,
        uploads: String?
    ) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        TechApiProvider(mApplication).saveInstallationData(
            userid,
            mobile,
            instalattionId,
            mExamId,
            ecId,
            ecmId,
            btsTower,
            btsTowerRoof,
            bigInstallJammer,
            smallInstallJammer,
            reserveBigJammer,
            reserveSmallJammer,
            jio,
            airtel,
            idea,
            other,
            bluetooth,
            jio3g,
            jio4g,
            airtel3g,
            airtel4g,
            idea3g,
            idea4g,
            other3g,
            other4g,
            signOff,
            latitude,
            longtitude,
            rooms,
            labs,
            date,
            time,
            uploads,
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
                                getString(R.string.msg_installation_data_save),
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
                    Log.e(RegistrationStep1Activity.TAG, "UserRegistration() $errorMsg")
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

    private fun getInstallationData(ecmId: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = mAppSharedPref?.getUserData()
        TechApiProvider(mApplication).getInstalationData(
            userDataModel?.userId,
            ecmId,
            object :
                RetroApiListener<List<InstallationDataModel>> {
                override fun onApiSuccess(response: List<InstallationDataModel>) {
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
                                setInstallationData(response[1])
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

    private fun setInstallationData(installationDataModel: InstallationDataModel?) {
        installationDataModel?.let {
            mInstallationId = it.installationId
            val date = CommonUtils.changeDatetimeFormat(
                it.date?.trim(),
                AppConstant.DATE_FORMAT_SERVER,
                AppConstant.DATE_FORMAT_DD_MM_YYYY
            )
            etDate.setText(date ?: "")
            val time = CommonUtils.changeDatetimeFormat(
                it.time?.trim(),
                AppConstant.DATE_FORMAT_SERVER,
                AppConstant.TIME_FORMAT
            )
            etTime.setText(time ?: "")
            etBtsTower.setText(it.bts_100_mtr ?: "")
            etBtsTowerRoof.setText(it.bts_roof ?: "")
            etBigInstallJammer.setText(it.installed_big_jammer ?: "")
            etSmallInstallJammer.setText(it.installed_small_jammer ?: "")
            etReserveBigJammer.setText(it.reserve_big_jammer ?: "")
            etReserveSmallJammer.setText(it.reserve_small_jammer ?: "")
            etRooms.setText(it.no_of_rooms ?: "")
            etLabs.setText(it.no_of_flor ?: "")
            etJio.setText(it.jio_signal_strength ?: "")
            etAirtel.setText(it.airtel_signal_strength ?: "")
            etIdea.setText(it.idea_signal_strength ?: "")
            etOther.setText(it.other_signal_strength ?: "")
            rbYesBluetooth.isChecked = "1" == it.bluetooth_jamming_status
            rbJio3gYes.isChecked = "1" == it.jio_3g_jamming_status
            rbJio4gYes.isChecked = "1" == it.jio_4g_jamming_status
            rbAirtel3gYes.isChecked = "1" == it.airtel_3g_jamming_status
            rbAirtel4gYes.isChecked = "1" == it.airtel_4g_jamming_status
            rbIdea3gYes.isChecked = "1" == it.idea_3g_jamming_status
            rbIdea4gYes.isChecked = "1" == it.idea_4g_jamming_status
            rbOther3gYes.isChecked = "1" == it.other_3g_jamming_status
            rbOther4gYes.isChecked = "1" == it.other_4g_jamming_status
            rbYesSignoff.isChecked = "1" == it.sign_off_status
            val images = it.uploads?.split("~")
            if (!images.isNullOrEmpty()) {
                mJammerPhotoList.clear()
                for (img in images) {
                    if (!TextUtils.isEmpty(img)) {
                        val jammerPhotoModel = JammerPhotoModel()
                        jammerPhotoModel.jammerImageName = img
                        jammerPhotoModel.jamerImagePath = AppConstant.IMAGE_BASE_URL + img
                        mJammerPhotoList.add(jammerPhotoModel)
                    }
                }
                val bigTower = Integer.parseInt(etBigInstallJammer.text.toString())
                val smallTower = Integer.parseInt(etSmallInstallJammer.text.toString())
                val totalJamer = (bigTower + smallTower - mJammerPhotoList.size)
                if (totalJamer <= 20) {
                    for (index in 0 until totalJamer) {
                        mJammerPhotoList.add(JammerPhotoModel())
                    }
                }
                mJammerPhotoAdapter?.notifyDataSetChanged()

                mJammerPhotoAdapter?.notifyDataSetChanged()
            }

        }
    }

    private fun clearData() {
        mJammerPhotoModel = null
        mInstallationId = null
        etDate.setText("")
        etTime.setText("")
        etBtsTower.setText("0")
        etBtsTowerRoof.setText("0")
        etBigInstallJammer.setText("0")
        etSmallInstallJammer.setText("0")
        etReserveBigJammer.setText("0")
        etReserveSmallJammer.setText("0")
        etRooms.setText("0")
        etLabs.setText("0")
        etJio.setText("0")
        etAirtel.setText("0")
        etIdea.setText("0")
        etOther.setText("0")
        rbNoBluetooth.isChecked = true
        rbJio3gNo.isChecked = true
        rbJio4gNo.isChecked = true
        rbAirtel3gNo.isChecked = true
        rbAirtel4gNo.isChecked = true
        rbIdea3gNo.isChecked = true
        rbIdea4gNo.isChecked = true
        rbOther3gNo.isChecked = true
        rbOther4gNo.isChecked = true
        rbNoSignoff.isChecked = true
        mJammerPhotoList.clear()
        mJammerPhotoAdapter?.notifyDataSetChanged()

    }


}
