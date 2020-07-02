package com.pratham.dse.ui.Techs.exam_schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.pratham.dse.R
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.exam_schedule.examination.ExamDatesActivity
import com.pratham.dse.ui.Techs.exam_schedule.installation.InstallationActivity
import com.pratham.dse.utils.BundleConstant
import kotlinx.android.synthetic.main.activity_exam_details.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ExamDetailsActivity : BaseActivity() {


    companion object {
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ExamDetailsActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_exam_details)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        readDataFromBundle()

    }

    private fun readDataFromBundle() {
        supportActionBar?.title = getString(R.string.title_exanm_details)

    }

    private fun initUI() {
        cvExamination.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                BundleConstant.KEY_EXAM_ID,
                intent.getStringExtra(BundleConstant.KEY_EXAM_ID)
            )
            ExamDatesActivity.startActivity(
                this@ExamDetailsActivity,
                bundle
            )
        }
        cvInstallation.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                BundleConstant.KEY_EXAM_ID,
                intent.getStringExtra(BundleConstant.KEY_EXAM_ID)
            )
            InstallationActivity.startActivity(
                this@ExamDetailsActivity,
                bundle
            )
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

}
