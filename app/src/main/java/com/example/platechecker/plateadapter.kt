package com.example.platechecker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.platechecker.data.PlateRecord
import java.text.SimpleDateFormat
import java.util.*

class PlateAdapter : ListAdapter<PlateRecord, PlateAdapter.PlateViewHolder>(PlateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return PlateViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val record = getItem(position)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val text = "${record.plateNumber} - ${dateFormat.format(Date(record.queryTime))} - ${if (record.isUsed) "已使用" else "未使用"}"
        holder.textView.text = text
    }

    class PlateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
}

class PlateDiffCallback : DiffUtil.ItemCallback<PlateRecord>() {
    override fun areItemsTheSame(oldItem: PlateRecord, newItem: PlateRecord): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlateRecord, newItem: PlateRecord): Boolean {
        return oldItem == newItem
    }
}