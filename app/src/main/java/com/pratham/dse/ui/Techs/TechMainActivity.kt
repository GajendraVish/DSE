package com.pratham.dse.ui.Techs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.pratham.dse.R
import com.pratham.dse.ui.BaseActivity
import com.pratham.dse.ui.Techs.exam_schedule.ExamScheduleActivity
import com.pratham.dse.ui.login.LoginActivity
import com.pratham.dse.ui.user.UpdateProfileActivity
import com.pratham.dse.utils.AppConstant
import com.pratham.dse.utils.AppUtils
import com.pratham.dse.utils.ImageUtil
import com.google.android.material.navigation.NavigationView
import com.pratham.dse.ui.Techs.conveyance.ConveyanceActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_tech_main.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class TechMainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private val TAG = TechMainActivity::class.java.simpleName
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, TechMainActivity::class.java)
            bundle?.let {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    private var ivProfile: ImageView? = null
    private var tvUserName: TextView? = null
    private var tvMobile: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        initUI()
    }

    private fun initUI() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val headerview = navView.getHeaderView(0)
        ivProfile = headerview?.findViewById(R.id.ivProfile)
        tvUserName = headerview.findViewById(R.id.tvUserName)
        tvMobile = headerview.findViewById(R.id.tvMobile)
        val userdata = mAppSharedPref?.getUserData()
        userdata?.let {
            tvUserName?.text = it.userName ?: ""
            tvMobile?.text = it.userMobile ?: ""
            ImageUtil.loadImage(it.imagePath, ivProfile, R.mipmap.ic_no_image)
            textUserName.text = it.userName ?: ""
        }
        time_text.text = AppUtils.getGreetingMessage() ?: ""
        ivProfile?.setOnClickListener {
            //            val userdata = mAppSharedPref?.getUserData()
//            if (userdata == null || TextUtils.isEmpty(userdata.userMobile)) {
//                LoginActivity.startActivity(
//                    this@MainActivity,
//                    null
//                )
//            }
        }
        cvExamSchedule.setOnClickListener {
            ExamScheduleActivity.startActivity(this@TechMainActivity, null)
        }
        cvConveyance.setOnClickListener {
            ConveyanceActivity.startActivity(this@TechMainActivity, null)
        }


    }


    private fun setUserName() {
        val userData = mAppSharedPref?.getUserData()

        if (userData == null || TextUtils.isEmpty(userData.userName)) {
            tvUserName?.text = getString(R.string.default_name)
        } else {
            tvUserName?.text = userData.userName
        }
        if (userData == null || TextUtils.isEmpty(userData.userMobile)) {
            tvMobile?.visibility = View.INVISIBLE
        } else {
            tvMobile?.visibility = View.VISIBLE
            tvMobile?.text = "Mob: ${userData.userMobile}"
        }


    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logout -> {
                mAppSharedPref?.clearData()
                LoginActivity.startActivity(mBaseActivity, null)
                finish()
            }
            R.id.nav_profile -> {
               UpdateProfileActivity.startActivity(this@TechMainActivity,null,AppConstant.PROFILE_ACTIVITY_FLAG)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
