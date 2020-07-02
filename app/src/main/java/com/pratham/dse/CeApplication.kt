package com.pratham.dse

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class CeApplication: Application() {
    private var sInstance: CeApplication? = null
    override fun onCreate() {
        super.onCreate()
        sInstance=this
        Stetho.initializeWithDefaults(this)
    }
    fun getApplicationInstance(): CeApplication? {
        return sInstance
    }
}