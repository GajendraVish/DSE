package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratham.dse.R
import com.pratham.dse.models.ExamShiftModel
import com.pratham.dse.utils.AppConstant
import com.bhaskar.utils.CommonUtils

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamDatesAdapter(
    private val activityContext: Context,
    private val onExamDatesClickListener: OnExamDatesClickListener,
    private val listData: MutableList<ExamShiftModel>
) : RecyclerView.Adapter<ExamDatesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(activityContext)
        val view = inflater.inflate(R.layout.adp_dates_itesm, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        item.examDate?.let {
            holder.tvDate.text = CommonUtils.changeDatetimeFormat(it,AppConstant.DATE_FORMAT_YYYY_MM_DD,AppConstant.DATE_FORMAT_DD_MMM_YYYY)
        }


        holder.itemView.setOnClickListener {
            onExamDatesClickListener.onExamDatesClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    }

    interface OnExamDatesClickListener {
        fun onExamDatesClick(item: ExamShiftModel)
    }
}
