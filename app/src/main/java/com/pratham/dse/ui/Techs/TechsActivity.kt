package com.pratham.dse.ui.Techs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.pratham.dse.R
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.exam_schedule.ExamScheduleActivity
import kotlinx.android.synthetic.main.activity_techs.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class TechsActivity : BaseActivity() {



    companion object {
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, TechsActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_techs)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()
        readDataFromBundle()

    }

    private fun readDataFromBundle() {
        supportActionBar?.title = "TECH'S"

    }

    private fun initUI() {
        cvExamSchedule.setOnClickListener {
            ExamScheduleActivity.startActivity(this@TechsActivity, null)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

}
