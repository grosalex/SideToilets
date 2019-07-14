package com.grosalex.sidetoilets.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grosalex.sidetoilets.R
import com.grosalex.sidetoilets.model.ToiletData

class ToiletViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(R.layout.toilet_item, parent, false)
) {
    private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    private val tvOpening: TextView = itemView.findViewById(R.id.tv_opening)
    private val tvManager: TextView = itemView.findViewById(R.id.tv_manager)

    fun bind(toiletData: ToiletData) {
        tvTitle.text = toiletData.title
        tvManager.text = toiletData.manager
        tvOpening.text = toiletData.opening
    }
}