package com.cypress.myapplication.fragments.media

import android.Manifest
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.NewActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.constants.*
import com.cypress.myapplication.databinding.FragmentMediaBinding
import com.cypress.myapplication.fragments.adapters.MediaAdapter
import com.cypress.myapplication.modeldatas.model.MediaItem
import com.cypress.myapplication.services.KEY
import com.cypress.myapplication.services.MediaService
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaFragment : Fragment(R.layout.fragment_media) {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MediaViewModel by viewModel()
    private lateinit var adapter: MediaAdapter
    private val broadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action?.let {
                adapter.setAction(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter()
        addIntentFilterActions(intentFilter)

        activity?.registerReceiver(broadcast, intentFilter)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NewActivity).setTitle("Media")

        setHasOptionsMenu(true)
        bindViews(view)

        (activity as NewActivity).setIsMediaPermGranted {
            if(it) {
            getMedias()
            } else {
                (activity as NewActivity).supportFragmentManager.popBackStack()
            }
        }

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            createList(it)
        }
        getMedias() //QUESTION: why do we call this function here?

    }

    private fun addIntentFilterActions(intentFilter: IntentFilter) {
        intentFilter.addAction(NOTIFICATION_SNOOZE_ACTION)
        intentFilter.addAction(NOTIFICATION_PLAY_ACTION)
        intentFilter.addAction(NOTIFICATION_PAUSE_ACTION)
    }

    private fun isMyServiceRunning(serviceClassName: String): Boolean {
        val manager = requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClassName == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun startService(mediaItem: MediaItem, action: String) {
        val intent = Intent(requireContext(), MediaService::class.java).apply {
            this.action = action
            putExtra(KEY, mediaItem)
        }

        (activity as NewActivity).startService(intent)
    }

    private fun startService(action: String) {
        val intent = Intent(requireContext(), MediaService::class.java).apply {
            this.action = action
        }
        (activity as NewActivity).startService(intent)
    }


    private fun getMedias() {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE}, 222) }
        } else activity?.let {viewModel.getMedias(it) }
    }

    private fun createList(list: List<MediaItem>) {
        adapter = MediaAdapter()

        adapter.setOnItemClickListener { mediaItem, action ->
            startService(mediaItem, action)
        }

        adapter.setOnPPClickListener {
            startService(it)
        }

        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter

        if (isMyServiceRunning(MediaService::class.java.name)) {
            val mediaItem = MediaService.curMedia
            val medList = list.toMutableList()
            for (i: Int in 0 until medList.size) {
                if (medList[i].id == mediaItem?.id) {
                    medList[i].isIconVisibile = mediaItem.isIconVisibile
                    medList[i].mode = mediaItem.mode
                    break
                }
            }
            adapter.mediaItems = medList
        }
        else  {
            adapter.mediaItems = list.toMutableList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcast)
    }

    private fun bindViews(view: View) {
        val binding = FragmentMediaBinding.bind(view)
        recyclerView = binding.mediaList
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MediaFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}