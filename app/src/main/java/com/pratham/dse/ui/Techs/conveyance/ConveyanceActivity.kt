package com.pratham.dse.ui.Techs.conveyance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.pratham.dse.R
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.conveyance.with_jammer.ConvenyanceLogWithJammerActivity
import com.pratham.dse.ui.Techs.conveyance.without_jammer.ConvenyanceLogWithoutJammerActivity
import kotlinx.android.synthetic.main.activity_conveyance.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class ConveyanceActivity : BaseActivity() {

    companion object {
        private val TAG = ConveyanceActivity::class.java.simpleName
        fun startActivity(context: Activity, bundle: Bundle?) {
            val intent = Intent(context, ConveyanceActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_conveyance)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initUI()


    }

    private fun initUI() {
        supportActionBar?.title = getString(R.string.conveyance_log)
        cvWithJammer.setOnClickListener {
            ConvenyanceLogWithJammerActivity.startActivity(this@ConveyanceActivity, null)
        }
        cvWithOutJammer.setOnClickListener {
            ConvenyanceLogWithoutJammerActivity.startActivity(this@ConveyanceActivity, null)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home) {
            onBackPressed() // close this activity
        }
        return super.onOptionsItemSelected(item)
    }

}




