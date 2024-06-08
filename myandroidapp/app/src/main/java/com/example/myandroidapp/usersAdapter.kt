package com.example.myandroidapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class usersAdapter (
    private val list : List<UserData>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<usersAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val email: TextView = itemView.findViewById(R.id.email)
        val imageProfile: ImageView = itemView.findViewById(R.id.image_profile)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val userData = list[position]
                    onItemClickListener.onItemClick(userData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = list[position]
        holder.userName.text = "${userData.firstName} ${userData.lastName}"
        holder.email.text = userData.email
        Glide.with(holder.itemView)
            .load(userData.avatar)
            .placeholder(R.drawable.profile_photo) // Placeholder image while loading
            .into(holder.imageProfile)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(userData: UserData)
    }

}