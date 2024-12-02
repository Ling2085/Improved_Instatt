package com.example.auth.studentActivityFile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.StudentHistory
import com.example.auth.databinding.RvHistoryBinding

class AbsentFormHistoryStudentAdapter(private val studentHistory: ArrayList<StudentHistory>) : RecyclerView.Adapter<AbsentFormHistoryStudentAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvHistoryBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return studentHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = studentHistory[position]
        holder.apply{
            binding.apply{
                tvApprovedOrReject.text = currentItem.approvedOrReject
                tvTimeHistory.text = currentItem.time
                tvDateHistory.text = currentItem.date
                tvCodeModule.text = currentItem.codeModule
            }
        }
    }

}