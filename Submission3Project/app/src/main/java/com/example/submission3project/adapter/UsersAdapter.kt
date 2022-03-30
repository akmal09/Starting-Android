package com.example.submission3project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3project.data.api.ItemsItem
import com.example.submission3project.data.entity.UserLikedEntity
import com.example.submission3project.databinding.UserListsBinding

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private lateinit var onClickItem: OnClickItem
    var mUserData  = ArrayList<UserLikedEntity>()

    fun setData(items: List<UserLikedEntity>) {
        mUserData.clear()
        mUserData.addAll(items)
        notifyDataSetChanged()
    }
    fun setOnItemClick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val bindingLayer = UserListsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(bindingLayer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mUserData[position])
    }

    override fun getItemCount(): Int = mUserData.size

    inner class ListViewHolder(private val binding: UserListsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userLikedEntity: UserLikedEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(userLikedEntity.avatarUrl)
                    .into(avaPhoto)
                name.text = userLikedEntity.login
                itemView.setOnClickListener{
                    onClickItem.onClicked(userLikedEntity)
                }
            }
        }
    }

    interface OnClickItem {
        fun onClicked(data : UserLikedEntity)
    }

}