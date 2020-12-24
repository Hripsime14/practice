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
import com.cypress.myapplication.constants.*
import com.cypress.myapplication.fragments.media.Mode
import com.cypress.myapplication.modeldatas.model.MediaItem

class MediaAdapter: RecyclerView.Adapter<MediaAdapter.MediaViewHolder>()  {
    private var oldItemPosition = -1

    private lateinit var onItemClickListener: (MediaItem, String) -> Unit

    fun setOnItemClickListener(onItemClickListener: (MediaItem, String) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }
    //PP = play, pause
    private lateinit var onPPClickListener: (String) -> Unit

    fun setOnPPClickListener(onPPClickListener: (String) -> Unit) {
        this.onPPClickListener = onPPClickListener
    }

    private val diffCallBackPhotos = object : DiffUtil.ItemCallback<MediaItem>() {
        override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }


    private val differPhotos = AsyncListDiffer(this, diffCallBackPhotos)
    var mediaItems: MutableList<MediaItem>
        set(value) {
            differPhotos.submitList(value)
        }
        get() = differPhotos.currentList.toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.MediaViewHolder {
        val view: View =  LayoutInflater.from(parent.context).inflate(R.layout.media_single_item, parent, false)
        return MediaViewHolder(view)
    }


    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(mediaItems[position])
    }


    override fun getItemCount() = mediaItems.count()

    fun setAction(action: String) {
        when(action) {
            NOTIFICATION_SNOOZE_ACTION -> {
                mediaItems[oldItemPosition].isIconVisibile = false
                notifyItemChanged(oldItemPosition)
            }
            NOTIFICATION_PAUSE_ACTION -> {
                mediaItems[oldItemPosition].mode = Mode.PAUSE
                notifyItemChanged(oldItemPosition)
            }
            NOTIFICATION_PLAY_ACTION -> {
                mediaItems[oldItemPosition].mode = Mode.PLAY
                notifyItemChanged(oldItemPosition)
            }
        }
    }


    inner class MediaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var name: TextView
        private lateinit var photo: ImageView
        private lateinit var pause: ImageView
        private lateinit var play: ImageView

        fun bind(mediaItem: MediaItem) {
            name = itemView.findViewById(R.id.fileName)
            photo = itemView.findViewById(R.id.fileImg)
            pause = itemView.findViewById(R.id.pauseImg)
            play = itemView.findViewById(R.id.startImg)
            name.text = mediaItems[adapterPosition].title
            if (!mediaItems[adapterPosition].isMusic) {
                photo.setImageResource(R.drawable.video)
            }
            else {
                photo.setImageResource(R.drawable.note)
            }


            if (!mediaItem.isIconVisibile) {
                pause.visibility = View.GONE
                play.visibility = View.GONE
            } else {
                if (mediaItem.mode == Mode.PLAY) {
                    pause.visibility = View.VISIBLE
                    play.visibility = View.GONE
                } else if (mediaItem.mode == Mode.PAUSE){
                    pause.visibility = View.GONE
                    play.visibility = View.VISIBLE
                }
            }

            pause.setOnClickListener {
                mediaItems[adapterPosition].mode = Mode.PAUSE
                mediaItems[adapterPosition].isIconVisibile = true
                notifyItemChanged(adapterPosition)
                onPPClickListener.invoke(PAUSE_ACTION)
            }

            play.setOnClickListener {
                mediaItems[adapterPosition].mode = Mode.PLAY
                mediaItems[adapterPosition].isIconVisibile = true
                notifyItemChanged(adapterPosition)
                onPPClickListener.invoke(PLAY_ACTION)
            }


            itemView.setOnClickListener {
                if (oldItemPosition != -1 && oldItemPosition != adapterPosition) {
                    mediaItems[oldItemPosition].isIconVisibile = false
                    mediaItems[oldItemPosition].mode = Mode.PAUSE
                    notifyItemChanged(oldItemPosition)
                }

                oldItemPosition = adapterPosition
                mediaItems[adapterPosition].isIconVisibile = true
                mediaItems[adapterPosition].mode = Mode.PLAY
                notifyItemChanged(adapterPosition)
                onItemClickListener.invoke(mediaItems[adapterPosition], START_ACTION)
            }
        }
    }
}