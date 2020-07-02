package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratham.dse.R

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class ExamShiftAdapter(
    private val activityContext: Context,
    private val onExamShiftClickListener: OnExamShiftClickListener,
    private val listData: MutableList<String>
) : RecyclerView.Adapter<ExamShiftAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(activityContext)
        val view = inflater.inflate(R.layout.adp_shift_itesm, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val item = mOrderList[position]
        holder.tvShiftName.text = listData[position]
//        holder.tvOrderDate.text = "Order Time: ${item.orderDateTime}"
//        holder.tvTotal.text = "Total Amount: ${item.totalAmount}"
        holder.itemView.setOnClickListener {
            onExamShiftClickListener.onExamShiftClick(listData[position])
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvShiftName: TextView = itemView.findViewById(R.id.tv_shift_name)
//        val tvOrderDate: TextView = itemView.findViewById(R.id.tv_order_date)
//        val tvTotal: TextView = itemView.findViewById(R.id.tv_total)

    }

    interface OnExamShiftClickListener {
        fun onExamShiftClick(shiftName: String)
    }
}
