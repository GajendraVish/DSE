package com.pratham.dse.ui.Techs.conveyance.with_jammer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.bhaskar.utils.DatePickerFragment
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.pratham.dse.R
import com.pratham.dse.adaptor.SpinnerCustomAdaptor
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.Utils
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_conveyance_log_with_jammer.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ConvenyanceLogWithJammerActivity : BaseActivity() {

    private var mLocation: Location? = null
    private var mediums: Array<String>? = null

    companion object {
        private val TAG = ConvenyanceLogWithJammerActivity::class.java.simpleName
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ConvenyanceLogWithJammerActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_conveyance_log_with_jammer)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()


    }

    override fun onResume() {
        super.onResume()
        checkAndGetLocation()
    }


    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_conveyance_log_with_jammer)
        mediums = resources.getStringArray(R.array.transport_medium)
        mediums?.let {
            travelMediumSpinner.adapter =
                SpinnerCustomAdaptor(mBaseActivity, it)
        }
        etDate.setOnClickListener {
            checkAndOpenDatePicker(etDate)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }


    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this@ConvenyanceLogWithJammerActivity,
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
                this@ConvenyanceLogWithJammerActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
//            if (SmartLocation.with(mBaseActivity).location().state().isGpsAvailable) {
            SmartLocation.with(this@ConvenyanceLogWithJammerActivity).location()
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

    private fun checkAndOpenDatePicker(
        etDate: EditText
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
                val datePickerFragment = DatePickerFragment(mBaseActivity)
                datePickerFragment.setOnDatePicked { day, month, year ->
                    etDate.setText("$day/$month/$year")
                }
                datePickerFragment.show(supportFragmentManager, "DatePicker")
            }
        }

    }


}
