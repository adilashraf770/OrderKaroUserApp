package com.adilashraf.orderkrouserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkrouserapp.databinding.NotificationItemsLayoutBinding
import com.adilashraf.orderkrouserapp.model.NotifyItems

class NotificationAdapter(val notifyList: List<NotifyItems> ): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ):  NotificationViewHolder {
        val nv = NotificationItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(nv)
        }

    override fun onBindViewHolder(
        holder:  NotificationViewHolder,
        position: Int,
    ) {
        val pos =  notifyList[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int  = notifyList.size

    class NotificationViewHolder(val binding: NotificationItemsLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: NotifyItems) {

            binding.apply {
                imageView.setImageResource(pos.img)
                txt.text = pos.txt
            }

        }

    }
}