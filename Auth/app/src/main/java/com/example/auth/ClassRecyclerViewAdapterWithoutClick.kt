package com.example.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.databinding.RvClassBinding
import com.example.auth.databinding.RvDialogStudentBinding

class ClassRecyclerViewAdapterWithoutClick(private val classList: ArrayList<Classes>) : RecyclerView.Adapter<ClassRecyclerViewAdapterWithoutClick.ViewHolder>() {
    class ViewHolder(val binding: RvDialogStudentBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvDialogStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return classList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = classList[position]
        holder.apply{
            binding.apply{
                tvStartTime.text = currentItem.startTime
                tvEndTime.text = currentItem.endTime
                tvVenue.text = currentItem.venue
                tvModuleCode.text = currentItem.codeModule
                tvModuleName.text = currentItem.nameModule
                tvLecturerOfTutorial.text = currentItem.lectureOrTutorial
            }
        }
    }

}