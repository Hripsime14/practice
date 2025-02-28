package com.cypress.myapplication.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.R
import com.cypress.myapplication.modeldatas.model.IntroItem

class ViewPager2Adapter: RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<IntroItem>() {
        override fun areItemsTheSame(oldItem: IntroItem, newItem: IntroItem): Boolean =
            oldItem.title == newItem.title


        override fun areContentsTheSame(oldItem: IntroItem, newItem: IntroItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var items: MutableList<IntroItem>
        set(value) = differ.submitList(value)
        get() = differ.currentList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_screen, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val desc: TextView  = itemView.findViewById(R.id.desc)
        private val img: ImageView = itemView.findViewById(R.id.img)
        fun bind(item: IntroItem) {
            title.text = item.title
            desc.text = item.description
            img.setImageResource(item.img)
        }
    }
}