package com.cypress.myapplication.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cypress.myapplication.R
import com.cypress.myapplication.modeldatas.model.ContactItem
import java.lang.System.load

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    private lateinit var ctx: Context //TODO: is this a terrible idea?
    private val diffCallBackPhotos = object : DiffUtil.ItemCallback<ContactItem>() {
        override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differPhotos = AsyncListDiffer(this, diffCallBackPhotos)
    var contactItems: MutableList<ContactItem>
        set(value) {
            differPhotos.submitList(value)
        }
        get() = differPhotos.currentList



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        ctx = parent.context
        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contacts_single_item, parent, false))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = contactItems.size

    inner class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var name: TextView
        private lateinit var photo: ImageView


        fun bind() {
            name = itemView.findViewById(R.id.contactFullName)
            name.text = contactItems[adapterPosition].fullName
            photo = itemView.findViewById(R.id.contactImage)
            if (contactItems[adapterPosition].photo != "") {
                Glide.with(ctx).load(contactItems[adapterPosition].photo.toUri())
                    .apply(RequestOptions.circleCropTransform()).into(photo)
            }
                photo.setBackgroundResource(R.drawable.cyrcle_border)

        }
    }
}