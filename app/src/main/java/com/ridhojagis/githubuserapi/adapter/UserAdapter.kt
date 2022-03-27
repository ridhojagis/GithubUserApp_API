package com.ridhojagis.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ridhojagis.githubuserapi.data.model.UserListItem
import com.ridhojagis.githubuserapi.databinding.ItemRowUserBinding

class UserAdapter(private val user: ArrayList<UserListItem>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class UserViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = user[position]
        holder.binding.tvUsername.text = user.username
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .circleCrop()
            .into(holder.binding.ivUser)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(this.user[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = user.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserListItem)
    }
}