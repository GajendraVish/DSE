package com.pratham.dse.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pratham.dse.CeApplication
import com.pratham.dse.R
import com.pratham.dse.utils.AppSharedPref
import com.bhaskar.utils.TransAviLoader

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
open class BaseActivity : AppCompatActivity() {
    lateinit var mApplication: CeApplication
    lateinit var mBaseActivity: BaseActivity
    var mAppSharedPref: AppSharedPref? = null
//    var mAviLoader: TransAviLoader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseActivity = this@BaseActivity
        mAppSharedPref = AppSharedPref(this@BaseActivity)
        mApplication = application as CeApplication

    }
}