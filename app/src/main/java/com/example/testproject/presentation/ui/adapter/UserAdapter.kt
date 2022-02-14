package com.example.testproject.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.data.repository.entity.User

class UserAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<ItemViewHolder>() {

    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItemViewHolder {
        return createViewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val user = getItem(position)
        viewHolder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    private fun getItem(position: Int): User = users[position]

    private fun createViewHolder(viewGroup: ViewGroup): ItemViewHolder {
        return ItemViewHolder.instance(viewGroup,listener)
    }

    fun setUsers(markets: List<User>) {
        this.users.clear()
        this.users.addAll(markets)
        notifyDataSetChanged()
    }

}

interface ItemClickListener {
    fun userClick(user: User)
}
