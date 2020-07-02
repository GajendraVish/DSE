package com.pratham.dse.utils

import android.text.TextUtils
import android.util.Patterns
import java.util.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class AppUtils {
    companion object {

        fun isValidEmail(target: String): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun getGreetingMessage(): String ?{
            val c = Calendar.getInstance()
            val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

            return when (timeOfDay) {
                in 0..11 -> "Good Morning"
                in 12..15 -> "Good Afternoon"
                in 16..20 -> "Good Evening"
                in 21..23 -> "Good Night"
                else -> {
                    ""
                }
            }
        }

    }
}
