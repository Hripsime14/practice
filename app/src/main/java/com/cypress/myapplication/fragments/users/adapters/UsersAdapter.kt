package com.cypress.myapplication.fragments.users.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.R
import com.cypress.myapplication.backend.UserEntity

class UsersAdapter(private val recyclerViewItemClick: RecyclerViewItemClickListener): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    interface RecyclerViewItemClickListener {
        fun recyclerViewItemClicked(userId: Int)
    }

    var onItemClickListener: (UserEntity) -> Unit = {}

    var data = mutableListOf<UserEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_single_item, parent, false)
        val viewHolder = UsersViewHolder(view)
        view.setOnClickListener {
            onItemClickListener.invoke(data[viewHolder.adapterPosition])
            recyclerViewItemClick.recyclerViewItemClicked(data[viewHolder.adapterPosition].id)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList(list: List<UserEntity>) {
        data= list.toMutableList()
        notifyDataSetChanged()
    }


    class UsersViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val username = view.findViewById<TextView>(R.id.username)
        private val email = view.findViewById<TextView>(R.id.email)
        fun bind(item: UserEntity) {
            username.text = item.username
            email.text = item.mail
        }
    }
}