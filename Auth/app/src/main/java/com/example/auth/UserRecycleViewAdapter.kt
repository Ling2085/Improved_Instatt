package com.example.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.databinding.RvUserBinding

class UserRecycleViewAdapter(private val userList : ArrayList<Users>, private val onItemClick: (Users) -> Unit) : RecyclerView.Adapter<UserRecycleViewAdapter.ViewHolder>(){
    class ViewHolder(val binding: RvUserBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.apply{
            binding.apply{
                tvName.text = currentItem.name
                tvID.text = currentItem.id
                root.setOnClickListener {
                    onItemClick(currentItem)
                }
            }
        }
    }
}