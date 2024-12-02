package com.example.auth.lecturerActivityFile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.LecturerHistory
import com.example.auth.databinding.RvHistoryLecturerBinding

class AbsentFormHistoryLecturerAdapter(private val lecturerHistory: ArrayList<LecturerHistory>) : RecyclerView.Adapter<AbsentFormHistoryLecturerAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvHistoryLecturerBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvHistoryLecturerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return lecturerHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = lecturerHistory[position]
        holder.apply {
            binding.apply {
                tvApprovedOrReject.text = currentItem.approvedOrReject
                tvTimeHistory.text = currentItem.time
                tvDateHistory.text = currentItem.date
                tvCodeModule.text = currentItem.codeModule
                tvNameStudent.text = currentItem.studentName
            }
        }
    }
}