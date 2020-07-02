package com.pratham.dse.ui.user

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.angel.accessories.provider.RegistrationApiProvider
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.imageprovider.imageCompression.ImageCompression
import com.imageprovider.imageCompression.ImageCompressionListener
import com.pratham.dse.R
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.models.UserRegistrationModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.pratham.dse.utils.ImageUtil
import com.pratham.dse.utils.Utils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_registration_step2.*
import java.io.File

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class RegistrationStep2NewActivity : BaseActivity() {
    private var mImageFilePath: String? = null

    companion object {

        fun startActivityForResult(context: Activity, bundle: Bundle?, flag: Int) {
            val intent = Intent(context, RegistrationStep2NewActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivityForResult(intent, flag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_registration_step2)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()

    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_registration)
        tvTakePhoto.setOnClickListener {
            checkAndOpenCamera()
        }
        tvNext.setOnClickListener {
            if (TextUtils.isEmpty(mImageFilePath)) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.msg_capture_image),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val userRegistrationModel =
                    intent.getParcelableExtra(BundleConstant.KEY_REG_USER) as UserRegistrationModel

                userRegistrationModel.let {
                    it.userImageName = File(mImageFilePath).name
                    it.userImagePath = mImageFilePath
                    uploadProfilePhoto(it)


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
                this@RegistrationStep2NewActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            if (Utils.checkPermission(
                    this@RegistrationStep2NewActivity,
                    Manifest.permission.CAMERA
                )
            ) {
                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this@RegistrationStep2NewActivity);
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
                                mImageFilePath = filePath
                                ImageUtil.loadImage(
                                    filePath,
                                    ivProfile,
                                    R.mipmap.ic_no_image
                                )
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

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this@RegistrationStep2NewActivity,
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

        }
    }

    private fun uploadProfilePhoto(userRegistrationModel: UserRegistrationModel) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity, R.color.matColor8))
        mAviLoader.show()
        val base64Str = ImageUtil.getFileToBase64(userRegistrationModel.userImagePath)
        RegistrationApiProvider(mApplication).uploadDocument(
            base64Str,
            userRegistrationModel.userImageName!!,
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
                            val intent = Intent()
                            intent.putExtra(BundleConstant.KEY_REG_USER, userRegistrationModel)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
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
}
