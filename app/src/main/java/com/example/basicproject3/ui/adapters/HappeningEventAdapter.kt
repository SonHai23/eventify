package com.example.basicproject3.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.HappeningEvent
import com.google.android.material.imageview.ShapeableImageView

class HappeningEventAdapter(private val items: ArrayList<HappeningEvent>) : RecyclerView.Adapter<HappeningEventAdapter.HappeningViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappeningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return HappeningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HappeningViewHolder, position: Int) {
        val currentItem = items[position]
//        holder.imageEvent.setImageResource(currentItem.imageEvent)
        holder.eventTitle.text = currentItem.title
//        holder.dateStart.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HappeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageEvent : ShapeableImageView = itemView.findViewById(R.id.imgEvent)
        val eventTitle : TextView = itemView.findViewById(R.id.txtEventTitle)
//        val dateStart : TextView = itemView.findViewById(R.id.txtEventTime)
    }
}