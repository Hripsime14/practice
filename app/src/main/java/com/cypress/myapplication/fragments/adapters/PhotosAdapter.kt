package com.cypress.myapplication.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cypress.myapplication.R
import com.cypress.myapplication.backend.PhotoEntity
import java.net.URL

class PhotosAdapter: RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private val diffCallBackPhotos = object : DiffUtil.ItemCallback<PhotoEntity>() {
        override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    private val differPhotos = AsyncListDiffer(this, diffCallBackPhotos)
    var photoItems: MutableList<PhotoEntity>
        set(value) {
            differPhotos.submitList(value)
        }
        get() = differPhotos.currentList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.photos_single_item, parent, false)

        return PhotosViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photoItems[position])
    }

    override fun getItemCount(): Int = photoItems.size

    inner class PhotosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.photo)

        fun bind(photoEntity: PhotoEntity) {
            val options = RequestOptions().placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder)
            val url = URL(photoEntity.url)
            Glide.with(photo).load(url).apply(options).into(photo)
        }
    }
}