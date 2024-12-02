package com.example.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.databinding.RvStudentAllclassBinding

class DateRecyclerViewAdapter(private val dateList: List<Date>,private val onItemClick: (Date) -> Unit) : RecyclerView.Adapter<DateRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvStudentAllclassBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvStudentAllclassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dateList[position]
        holder.apply {
            binding.apply {
                tvDate.text = currentItem.date
                root.setOnClickListener {
                    onItemClick(currentItem) // Trigger the click event
                }
            }
        }
    }
}
//,private val onItemClick: (Date) -> Unit