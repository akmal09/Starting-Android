package com.example.submission1project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(private val listUser:ArrayList<User>): RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private lateinit var onItemClickAva: OnItemClickAva

    fun setOnItemClickAva(onItemClickAva: OnItemClickAva) {
        this.onItemClickAva = onItemClickAva
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.user_lists, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(username, name, location, repository, company, followers, following, avatar) = listUser[position]
        holder.avaPhoto.setImageResource(avatar)
        holder.company.text = name
        holder.location.text = location
        holder.company.text = company
        holder.avaPhoto.setOnClickListener{
            onItemClickAva.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avaPhoto: ImageView = itemView.findViewById(R.id.ava_photo)
        var name: TextView = itemView.findViewById(R.id.name)
        var location: TextView = itemView.findViewById(R.id.location)
        var company: TextView = itemView.findViewById(R.id.company)
    }

    interface OnItemClickAva{
        fun onItemClicked(data : User)
    }
}