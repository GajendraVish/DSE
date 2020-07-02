package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pratham.dse.R
import com.pratham.dse.models.JammerPhotoModel
import com.pratham.dse.utils.ImageUtil

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
class JammerPhotoAdapter(
    private val activityContext: Context,
    private val onJammerItemClickListener: OnJammerItemClickListener,
    private val mJammerPhotoList: MutableList<JammerPhotoModel>
) : RecyclerView.Adapter<JammerPhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(activityContext)
        val view = inflater.inflate(R.layout.adp_jammer_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.tvJammerName.text = "J${position + 1}"
        mJammerPhotoList[position].jamerImagePath?.let {
            ImageUtil.loadImage(it, holder.ivJammer, R.mipmap.ic_no_image)
        }
        holder.itemView.setOnClickListener {
            mJammerPhotoList[position].ivJammmer = holder.ivJammer
            mJammerPhotoList[position].ivJammmer?.let {
                onJammerItemClickListener.onJammerItemClick(mJammerPhotoList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return mJammerPhotoList.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvJammerName: TextView = itemView.findViewById(R.id.tvJammerName)
        val ivJammer: ImageView = itemView.findViewById(R.id.ivJammer)

    }

    interface OnJammerItemClickListener {
        fun onJammerItemClick(jammer: JammerPhotoModel)
    }
}
