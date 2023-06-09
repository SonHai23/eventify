package com.example.basicproject3.ui.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicproject3.EventActivity
import com.example.basicproject3.R
import com.example.basicproject3.data.Utils.Companion.formatDate
import com.example.basicproject3.data.model.Event
import com.google.android.material.imageview.ShapeableImageView

class EventListAdapter(
    private val context: Context,
    private val dataset: List<Event>
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)

        return EventViewHolder(adapterLayout)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ShapeableImageView = view.findViewById(R.id.imgEvent)
        val textViewTitle: TextView = view.findViewById(R.id.txtEventTitle)
        val textViewEventDuration: TextView = view.findViewById(R.id.txtEventStartTime)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = dataset[position]
        event.getImgUrl().addOnSuccessListener {
            Glide.with(context).load(it).into(holder.imgView)
        }

        holder.textViewTitle.text = event.title
        holder.textViewEventDuration.text = formatDate(event.date_start)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EventActivity::class.java)
            val bundle = Bundle().apply {
                putParcelable("event", event)
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset.size
}