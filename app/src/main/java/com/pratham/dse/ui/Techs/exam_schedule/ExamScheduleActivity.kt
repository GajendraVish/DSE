package com.pratham.dse.ui.Techs.exam_schedule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.accessories.provider.TechApiProvider
import com.pratham.dse.R
import com.pratham.dse.adaptor.ExamListAdapter
import com.pratham.dse.models.ExamListModel
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.user.RegistrationStep1Activity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.BundleConstant
import com.bhaskar.network.RetroApiListener
import com.bhaskar.utils.DialogOkInterface
import com.bhaskar.utils.EasyDialogUtils
import com.bhaskar.utils.TransAviLoader
import kotlinx.android.synthetic.main.activity_exam_schedule.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ExamScheduleActivity : BaseActivity(), ExamListAdapter.OnExamClickListener {


    companion object {
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ExamScheduleActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_exam_schedule)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        val userDataModel = mAppSharedPref?.getUserData()
        userDataModel?.let {
            if (!TextUtils.isEmpty(it.userId) && !TextUtils.isEmpty(it.userMobile)) {
                getExamList(it.userId!!, it.userMobile!!)
            }
        }


    }

    private fun getExamList(userId: String, mobileNo: String) {
        val mAviLoader = TransAviLoader(mBaseActivity, true)
        mAviLoader.setLoaderColor(ContextCompat.getColor(mBaseActivity,R.color.matColor8))
        mAviLoader.show()
        TechApiProvider(mApplication).getExamList(userId, mobileNo, object :
            RetroApiListener<MutableList<ExamListModel>> {
            override fun onApiSuccess(response: MutableList<ExamListModel>) {
                if(mAviLoader.isShowing){
                    mAviLoader.dismiss()
                }
                if (!response.isNullOrEmpty()) {
                    if (AppConstant.STATUS_SUCCESS.equals(response[0].status, ignoreCase = true)) {
                        tvEmptyText.visibility = View.GONE
                        rvItemList.visibility = View.VISIBLE
                        response.removeAt(0)
                        if (!response.isEmpty()) {
                            val mAdapter =
                                ExamListAdapter(mBaseActivity, this@ExamScheduleActivity, response)
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
                Log.e(RegistrationStep1Activity.TAG, "getExamList() $errorMsg")
                tvEmptyText.text = errorMsg
                tvEmptyText.visibility = View.VISIBLE
                rvItemList.visibility = View.GONE

            }
        })

    }


    private fun initUI() {
        supportActionBar?.title = getString(R.string.title_exam_schedule)
        // set up the RecyclerView
        rvItemList.layoutManager = LinearLayoutManager(this)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExamClick(examListModel: ExamListModel) {
        val bundle = Bundle()
        bundle.putString(BundleConstant.KEY_EXAM_ID, examListModel.examId)
        ExamDetailsActivity.startActivity(
            this@ExamScheduleActivity,
            bundle
        )
    }
}
