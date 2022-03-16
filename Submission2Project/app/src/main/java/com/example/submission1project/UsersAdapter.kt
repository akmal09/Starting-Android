package com.example.submission1project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1project.databinding.UserListsBinding

class UsersAdapter(private val listUser:ArrayList<User>): RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private lateinit var onItemClickAva: OnItemClickAva

    fun setOnItemClickAva(onItemClickAva: OnItemClickAva) {
        this.onItemClickAva = onItemClickAva
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val bindingLayer = UserListsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(bindingLayer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(_, name, location, _, company, _, _, avatar) = listUser[position]
        holder.apply {
            binding.avaPhoto.setImageResource(avatar)
            binding.name.text = name
            binding.location.text = location
            binding.company.text = company
            itemView.setOnClickListener{
                onItemClickAva.onItemClicked(listUser[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: UserListsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickAva{
        fun onItemClicked(data : User)
    }
}