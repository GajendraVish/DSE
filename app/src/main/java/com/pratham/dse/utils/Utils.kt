package com.pratham.dse.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.content.ContextCompat
import com.pratham.dse.ui.BaseActivity
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {
        fun shareContent(activity: BaseActivity) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?UserId=" + activity.applicationContext.packageName
            )
            sendIntent.type = "text/plain"
            activity.startActivity(sendIntent)
        }

        fun callNumber(phoneN: String?, activity: BaseActivity) {
            phoneN?.let {
                val encodedPhoneNumber = String.format("tel:%s", Uri.encode(it))
                val number = Uri.parse(encodedPhoneNumber)
                val callIntent = Intent(Intent.ACTION_DIAL, number)
                activity.startActivity(callIntent)
            }
        }

        fun getHtmlText(text: String): Spanned {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(text);
            }


        }

        fun checkPermission(context: Context, permission: String): Boolean {
            val result = ContextCompat.checkSelfPermission(
                context, permission

            )
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun getDateDiff(dateString: String): Long {
            val sdf = SimpleDateFormat(AppConstant.DATE_FORMAT_YYYY_MM_DD)
            val date = sdf.parse(dateString)
            val now = Calendar.getInstance()
            val yourDate = Calendar.getInstance()
            yourDate.timeInMillis = date.time
            return yourDate.timeInMillis - now.timeInMillis

        }

    }
}