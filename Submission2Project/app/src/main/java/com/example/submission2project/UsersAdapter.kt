package com.example.submission2project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2project.databinding.UserListsBinding

class UsersAdapter(private val dataUsers:List<ItemsItem>): RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private lateinit var onClickItem: OnClickItem

    fun setOnItemClick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val bindingLayer = UserListsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(bindingLayer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(login,_,avatarUrl) = dataUsers[position]
        holder.apply {
            Glide.with(itemView.context)
                .load(avatarUrl)
                .circleCrop()
                .into(binding.avaPhoto)
            binding.name.text = login
            itemView.setOnClickListener{
                onClickItem.onClicked(dataUsers[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = dataUsers.size

    class ListViewHolder(var binding: UserListsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickItem {
        fun onClicked(data : ItemsItem)
    }

}