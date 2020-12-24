package com.cypress.myapplication.fragments.users.albums

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.Status
import com.cypress.myapplication.backend.AlbumEntity
import com.cypress.myapplication.backend.PhotoEntity
import com.cypress.myapplication.databinding.FragmentAlbumsBinding
import com.cypress.myapplication.fragments.adapters.AlbumsAdapter
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private lateinit var binding: FragmentAlbumsBinding
    private var viewModel: AlbumsViewModel? = null
    private var userId by Delegates.notNull<Int>()
    private lateinit var list: RecyclerView
    private var adapter: AlbumsAdapter? = null
    private val map = HashMap<Int, MutableList<PhotoEntity>>()
    private var albumList = mutableListOf<AlbumEntity>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userId = it.getInt(USER_ID_KEY)
            viewModel = getViewModel { parametersOf(userId) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumsBinding.bind(view)
        list = binding.albumsList
        swipeRefreshLayout = binding.albumsSwipe
        setActionBarTitle()

        observeData()
        setListeners()
    }

    private fun setActionBarTitle() {
        (activity as PracticeActivity).setTitle("Albums")
    }

    private fun observeData() {
        //albums
        viewModel?.getLocalAlbums()?.observe(viewLifecycleOwner) {
            it?.let {
                albumList = it.toMutableList()
            }
        }

        //photos
        viewModel?.getLocalPhotos()?.observe(viewLifecycleOwner) {
            it?.let {
                for(item in it)  {
                    if (!map.containsKey(item.albumId)) {
                        map[item.albumId] = mutableListOf()
                    }
                    map[item.albumId]?.add(item)
                }
                createList()
            }
        }

        viewModel?.albumsLiveData?.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> Unit
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel?.photoLiveData?.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> Unit
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setListeners() {
        val handler = Handler(Looper.getMainLooper())
        var runnable: Runnable
        swipeRefreshLayout.setOnRefreshListener {
            runnable = Runnable {
                map.clear()
                viewModel?.clearPhotoList()
                viewModel?.getAlbums(userId)
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(runnable, 1500L)
        }
    }

    private fun createList() {
        adapter = AlbumsAdapter(map)
        this.list.layoutManager = LinearLayoutManager(this.context)
        this.list.adapter = adapter
        adapter?.items = albumList
    }

    companion object {
        private const val USER_ID_KEY = "user_id"
        @JvmStatic
        fun newInstance(userId: Int) =
            AlbumsFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID_KEY, userId)
                }
            }
    }

    override fun onDestroyView() {
        viewModel?.clearRoom()
        super.onDestroyView()
    }
}