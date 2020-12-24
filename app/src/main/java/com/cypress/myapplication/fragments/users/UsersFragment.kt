package com.cypress.myapplication.fragments.users

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.Status
import com.cypress.myapplication.backend.UserEntity
import com.cypress.myapplication.databinding.FragmentUsersBinding
import com.cypress.myapplication.fragments.adapters.UsersAdapter
import com.cypress.myapplication.fragments.users.albums.AlbumsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment(R.layout.fragment_users) {
    private lateinit var binding: FragmentUsersBinding
    private val viewModel: UsersViewModel by viewModel()
    private lateinit var list: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var adapter : UsersAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as PracticeActivity).setTitle("Users")
        binding = FragmentUsersBinding.bind(view)
        list = binding.usersList
        swipeRefreshLayout = binding.usersSwipe

        observeUsers()
        setListeners()

        viewModel.liveData.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> Unit
                Status.LOADING-> {}
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun createList(list: List<UserEntity>) {
        adapter = UsersAdapter(object : UsersAdapter.RecyclerViewItemClickListener {
            override fun recyclerViewItemClicked(userId: Int) {
                (activity as PracticeActivity).replaceFragment(AlbumsFragment.newInstance(userId))
            }
        })

        this.list.layoutManager = LinearLayoutManager(this.context)
        this.list.adapter = adapter
        adapter?.setList(list)
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            val handler =  Handler(Looper.getMainLooper())
            val runnable = Runnable {
                viewModel.getUsers()
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(runnable, 1500.toLong())
        }
    }

    private fun observeUsers() {
        viewModel.getLocalUsers().observe(viewLifecycleOwner) {
            it?.let {
                createList(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UsersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}