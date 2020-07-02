package com.pratham.dse

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import com.angel.accessories.provider.TechApiProvider
import com.bhaskar.dialogs.BaseDialog
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.pratham.dse.models.BaseResponseModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.utils.AppConstant
import kotlinx.android.synthetic.main.dialog_issue.*

class RemarkIssueDialog(
    private val activity: BaseActivity,
    private val message: String?,
    private val mShiftName: String?,
    private val mEcmID: String?,
    private val latitude: String?,
    private val logtitude: String?,
    private val mShiftId: String?,
    private val onRemarkUpdateListener: RemarkUpdateListener
) :

    BaseDialog(activity) {
    companion object {
        private val TAG = RemarkIssueDialog::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_issue)
        etMarkIssue.setText(message ?: "")
        setCancelable(false)
        tvBtnCancel.setOnClickListener {
            dismiss()
        }
        tvBtnSubmit.setOnClickListener {
            val remrk = etMarkIssue.text.toString().trim()
            if (TextUtils.isEmpty(remrk)) {
                EasyDialogUtils.showOkDialog(
                    activity,
                    activity.getString(R.string.app_name),
                    activity.getString(R.string.msg_please_enter_remarks),
                    object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {
                        }

                    }
                )
            } else {

                when (mShiftName) {
                    AppConstant.SHIFT_1 -> {
                        remarkIssue(
                            AppConstant.URL_SAVE_EXAM_SHIFT_I_ISSUE,
                            mEcmID,
                            mShiftId,
                            latitude,
                            logtitude, remrk
                        )
                    }
                    AppConstant.SHIFT_2 -> {
                        remarkIssue(
                            AppConstant.URL_SAVE_EXAM_SHIFT_II_ISSUE, mEcmID,
                            mShiftId,
                            latitude,
                            logtitude, remrk
                        )
                    }
                    AppConstant.SHIFT_3 -> {
                        remarkIssue(
                            AppConstant.URL_SAVE_EXAM_SHIFT_III_ISSUE, mEcmID,
                            mShiftId,
                            latitude,
                            logtitude, remrk
                        )

                    }
                    AppConstant.SHIFT_4 -> {
                        remarkIssue(
                            AppConstant.URL_SAVE_EXAM_SHIFT_IV_ISSUE, mEcmID,
                            mShiftId,
                            latitude,
                            logtitude, remrk
                        )

                    }

                }
            }
        }
    }

    private fun remarkIssue(
        fullUrl: String,
        tyecm_id: String?,
        shiftid: String?, latitude: String?, longtitude: String?,
        remark: String?
    ) {
        val mAviLoader = TransAviLoader(activity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(activity, R.color.matColor8))
        mAviLoader.show()
        val userDataModel = activity.mAppSharedPref?.getUserData()
        TechApiProvider(activity.mApplication).remarkIssue(
            fullUrl,
            userDataModel?.userId,
            userDataModel?.userMobile, tyecm_id,
            shiftid, latitude, longtitude,
            remark,
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
                                activity,
                                activity.getString(R.string.app_name),
                                activity.getString(R.string.msg_save_remark),
                                object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {
                                        dismiss()
                                        onRemarkUpdateListener.onRemarkUpdateSuccess(remark)
                                    }

                                }
                            )

                        } else {
                            EasyDialogUtils.showInfoDialog(
                                activity,
                                activity.getString(R.string.app_name),
                                response[0].msg!!
                            )
                        }
                    } else {
                        EasyDialogUtils.showInfoDialog(
                            activity,
                            activity.getString(R.string.app_name),
                            activity.getString(R.string.msg_null_data)
                        )
                    }
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    Log.e(TAG, "remarkIssue() $errorMsg")
                    if (mAviLoader.isShowing) {
                        mAviLoader.dismiss()
                    }
                    EasyDialogUtils.showOkDialog(
                        activity,
                        activity.getString(R.string.app_name),
                        errorMsg, object : DialogOkInterface {
                            override fun doOnOkBtnClick(activity: Activity) {
                            }

                        }
                    )
                }
            })


    }

    interface RemarkUpdateListener {
        fun onRemarkUpdateSuccess(remark: String?)
    }
}