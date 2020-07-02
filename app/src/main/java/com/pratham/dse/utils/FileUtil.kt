package com.pratham.dse.utils

import com.pratham.dse.ui.BaseActivity
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class FileUtil {

    companion object {
        fun AssetJSONFile(filename: String, context: BaseActivity?): String? {
            context?.let {
                val manager = it.assets
                val file = manager.open(filename)
                val formArray = ByteArray(file.available())
                file.read(formArray)
                file.close()
                return String(formArray)
            }
            return null
        }
    }
}