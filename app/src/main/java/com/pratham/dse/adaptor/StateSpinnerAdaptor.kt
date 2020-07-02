package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.pratham.dse.R
import com.pratham.dse.models.StateModel

class StateSpinnerAdaptor(
    val context: Context,
    val stateList: MutableList<StateModel>
) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        // TODO Auto-generated constructor stub
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return stateList.size
    }

    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return position
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        val tv: TextView
        if (convertView == null) {
            tv = inflater.inflate(R.layout.adp_spinner, null) as TextView
        } else {
            tv = (convertView as TextView?)!!
        }
        tv.text = stateList[position].stateName
        return tv
    }

}
