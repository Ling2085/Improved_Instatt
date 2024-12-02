package com.example.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.databinding.RvClassBinding

class ClassRecycleViewAdapter(private val classList: ArrayList<Classes>, private val onItemClick: (Classes) -> Unit) : RecyclerView.Adapter<ClassRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvClassBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvClassBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
                tvSignIn.text = currentItem.signIn
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

}