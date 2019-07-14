package com.grosalex.sidetoilets.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grosalex.sidetoilets.model.ToiletData
import com.grosalex.sidetoilets.viewholder.ToiletViewHolder

class ToiletsAdapter(var list: List<ToiletData>) : RecyclerView.Adapter<ToiletViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletViewHolder = ToiletViewHolder(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ToiletViewHolder, position: Int) {
        holder.bind(list[position])
    }
}