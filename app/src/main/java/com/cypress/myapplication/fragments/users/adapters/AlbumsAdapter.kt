package com.cypress.myapplication.fragments.users.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.R
import com.cypress.myapplication.backend.AlbumEntity
import com.cypress.myapplication.backend.PhotoEntity

class AlbumsAdapter(private val map: Map<Int, MutableList<PhotoEntity>>): RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    private var tempPhotosList: MutableList<PhotoEntity> = mutableListOf()
    //albums
    private val diffCallBack = object : DiffUtil.ItemCallback<AlbumEntity>() {
        override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    var items: MutableList<AlbumEntity>
        set(value) {
            differ.submitList(value)
        }
        get() = differ.currentList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val view = LayoutInflater.from(parent.context).
            inflate(R.layout.albums_single_item, parent, false)

        return AlbumsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        if (map.containsKey(items[position].id)) {
            tempPhotosList = map[items[position].id] ?: error("")
            holder.bind(tempPhotosList, items[position])
            Log.d("mnmnnmnmn", "onBindViewHolder: size= ${map.get(1)?.size}")
        }
    }

    override fun getItemCount() = items.size

    inner class AlbumsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var photosList: RecyclerView
        lateinit var albumTitle: TextView

        fun bind(photos: MutableList<PhotoEntity>, album: AlbumEntity) {
            photosList = itemView.findViewById(R.id.photosList)
            albumTitle = itemView.findViewById(R.id.albumName)
            albumTitle.text = album.name
            val photosAdapter = PhotosAdapter()
            photosList.adapter = photosAdapter
            photosList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            photosAdapter.photoItems = photos
        }
    }
}