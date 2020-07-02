package com.pratham.dse.ui.Techs.exam_schedule.examination

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.accessories.provider.TechApiProvider
import com.pratham.dse.R
import com.pratham.dse.adaptor.ExamDatesAdapter
import com.pratham.dse.models.ExamShiftModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import com.pratham.dse.utils.Utils
import kotlinx.android.synthetic.main.activity_exam_dates.*
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ExamDatesActivity : BaseActivity(), ExamDatesAdapter.OnExamDatesClickListener {


    companion object {
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ExamDatesActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_exam_dates)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        readDataFromBundle()


    }


    private fun readDataFromBundle() {
        val examId = intent.getStringExtra(BundleConstant.KEY_EXAM_ID)
        examId?.let {
            getExamShifts(examId)
        }


    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_exam_dates)
        // set up the RecyclerView
        rvItemList.layoutManager = LinearLayoutManager(this)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExamDatesClick(item: ExamShiftModel) {
        val eDate=item.examDate
        eDate?.let {
            if(Utils.getDateDiff(it)>0){
                EasyDialogUtils.showOkDialog(
                    mBaseActivity,
                    getString(R.string.app_name),
                    getString(R.string.msg_future_date_is_disable_now), object : DialogOkInterface {
                        override fun doOnOkBtnClick(activity: Activity) {

                        }

                    }
                )
            }else{
                val bundle = Bundle()
                bundle.putString(BundleConstant.KEY_EXAM_ID, item.examId)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ID, item.sdId)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ONE_START_TIME, item.shiftOneStartTime)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ONE_TWO_TIME, item.shiftTwoStartTime)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ONE_THREE_TIME, item.shiftThreeStartTime)
                bundle.putString(BundleConstant.KEY_EXAM_SHIFT_ONE_FOUR_TIME, item.shiftFourStartTime)
                ExamShiftActivity.startActivity(mBaseActivity, bundle)
            }

        }

    }


    private fun getExamShifts(examId: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
         mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity,R.color.matColor8))
        mAviLoader.show()
        TechApiProvider(mApplication).getExamShifts(
            mAppSharedPref?.getUserData()?.userId,
            examId,
            object :
                RetroApiListener<MutableList<ExamShiftModel>> {
                override fun onApiSuccess(response: MutableList<ExamShiftModel>) {
                    if(mAviLoader.isShowing){
                        mAviLoader.dismiss()
                    }
                    if (!response.isNullOrEmpty()) {
                        if (AppConstant.STATUS_SUCCESS.equals(
                                response[0].status,
                                ignoreCase = true
                            )
                        ) {
                            tvEmptyText.visibility = View.GONE
                            rvItemList.visibility = View.VISIBLE
                            response.removeAt(0)
                            if (!response.isEmpty()) {
                                val mAdapter = ExamDatesAdapter(
                                    mBaseActivity,
                                    this@ExamDatesActivity,
                                    response
                                )
                                rvItemList.adapter = mAdapter
                            } else {
                                tvEmptyText.visibility = View.VISIBLE
                                rvItemList.visibility = View.GONE
                            }

                        } else {
                            EasyDialogUtils.showOkDialog(
                                mBaseActivity,
                                getString(R.string.app_name),
                                response[0].msg!!, object : DialogOkInterface {
                                    override fun doOnOkBtnClick(activity: Activity) {

                                    }

                                }
                            )
                        }
                    } else {
                        EasyDialogUtils.showOkDialog(
                            mBaseActivity,
                            getString(R.string.app_name),
                            response[0].msg!!, object : DialogOkInterface {
                                override fun doOnOkBtnClick(activity: Activity) {

                                }

                            }
                        )
                    }
                }

                override fun onApiFailed(errorMsg: String) {
                    if(mAviLoader.isShowing){
                        mAviLoader.dismiss()
                    }
                    Log.e(RegistrationStep1Activity.TAG, "getExamShifts() $errorMsg")
                    tvEmptyText.text = errorMsg
                    tvEmptyText.visibility = View.VISIBLE
                    rvItemList.visibility = View.GONE

                }
            })

    }

}
