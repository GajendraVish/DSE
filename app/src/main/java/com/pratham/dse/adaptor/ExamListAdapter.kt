package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratham.dse.R
import com.pratham.dse.models.ExamListModel
import com.pratham.dse.utils.AppConstant
import com.bhaskar.utils.CommonUtils

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamListAdapter(
    private val activityContext: Context,
    private val onExamClickListener: OnExamClickListener,
    private val examList: MutableList<ExamListModel>
) : RecyclerView.Adapter<ExamListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(activityContext)
        val view = inflater.inflate(R.layout.adp_exam_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = examList[position]
        holder.tvExamName.text = item.examName ?: ""
        item.examDate?.let {
            holder.tv_date.text = CommonUtils.changeDatetimeFormat(it,AppConstant.DATE_FORMAT_YYYY_MM_DD,AppConstant.DATE_FORMAT_DD_MMM_YYYY)
        } ?: run {
            holder.tv_date.text = ""
        }


        holder.itemView.setOnClickListener {
            onExamClickListener.onExamClick(item)
        }
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvExamName: TextView = itemView.findViewById(R.id.tv_exam_name)
        val tv_date: TextView = itemView.findViewById(R.id.tv_date)

    }

    interface OnExamClickListener {
        fun onExamClick(examListModel: ExamListModel)
    }
}
