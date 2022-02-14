package com.example.testproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.data.repository.entity.User
import com.example.testproject.databinding.ItemUserBinding

class ItemViewHolder(
    private val binding: ItemUserBinding,
    private val listener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            tvName.text = "${user.firstName} ${user.lastName}"
            Glide.with(ivAvatar.context)
                .load(user.avatar)
                .circleCrop()
                .into(ivAvatar)
        }

        itemView.setOnClickListener { listener.userClick(user) }
    }

    companion object {
        fun instance(parent: ViewGroup, listener: ItemClickListener): ItemViewHolder {
            val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding, listener)
        }
    }
}
