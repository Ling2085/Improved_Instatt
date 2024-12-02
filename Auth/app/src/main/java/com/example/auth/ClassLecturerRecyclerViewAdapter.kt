package com.example.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.databinding.RvClassLecturerBinding

class ClassLecturerRecycleViewAdapter(private val classList: ArrayList<Classes>, private val onItemClick: (Classes)-> Unit) : RecyclerView.Adapter<ClassLecturerRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvClassLecturerBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvClassLecturerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
                root.setOnClickListener {
                    onItemClick(currentItem) // Trigger the click event
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return classList.size
    }

}
