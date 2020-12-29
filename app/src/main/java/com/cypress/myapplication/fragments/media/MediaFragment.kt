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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.R
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.constants.NOTIFICATION_PAUSE_ACTION
import com.cypress.myapplication.constants.NOTIFICATION_PLAY_ACTION
import com.cypress.myapplication.constants.NOTIFICATION_SNOOZE_ACTION
import com.cypress.myapplication.databinding.FragmentMediaBinding
import com.cypress.myapplication.fragments.BaseFragment
import com.cypress.myapplication.fragments.adapters.MediaAdapter
import com.cypress.myapplication.modeldatas.model.MediaItem
import com.cypress.myapplication.services.KEY
import com.cypress.myapplication.services.MediaService
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaFragment : BaseFragment(R.layout.fragment_media) {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MediaViewModel by viewModel()
    private lateinit var adapter: MediaAdapter
    private val broadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action?.let {
                Log.d("bbbbbbbbbbbb", "onReceive: ")
                adapter.setAction(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter()
        addIntentFilterActions(intentFilter)

        activity?.registerReceiver(broadcast, intentFilter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Media")

        bindViews(view)

        (activity as PracticeActivity).setIsMediaPermGranted {
            if(it) {
                getPermission()
            } else {
                (activity as PracticeActivity).supportFragmentManager.popBackStack()
            }
        }

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            createList(it)
        }
        getPermission()

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

        (activity as PracticeActivity).startService(intent)
    }

    private fun startService(action: String) {
        val intent = Intent(requireContext(), MediaService::class.java).apply {
            this.action = action
        }
        (activity as PracticeActivity).startService(intent)
    }

    private fun getPermission() {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE}, 222) }
        } else getMedias()
    }

    private fun getMedias() {
        activity?.let {viewModel.getMedias(it) }
    }

    private fun createList(list: List<MediaItem>) {
        adapter = MediaAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickListener { mediaItem, action ->
            startService(mediaItem, action)
        }

        adapter.setOnPPClickListener {
            startService(it)
        }

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