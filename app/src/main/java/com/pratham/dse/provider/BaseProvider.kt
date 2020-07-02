package com.angel.accessories.provider


import com.pratham.dse.CeApplication

abstract class BaseProvider {
    protected var mApplication: CeApplication? = null

    constructor(application: CeApplication) {
        this.mApplication = application
    }
}
