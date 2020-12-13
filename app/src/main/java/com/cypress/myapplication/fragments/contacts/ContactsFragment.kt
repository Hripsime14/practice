
package com.cypress.myapplication.fragments.contacts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.NewActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.databinding.FragmentContactsBinding
import com.cypress.myapplication.fragments.adapters.ContactsAdapter
import com.cypress.myapplication.modeldatas.model.ContactItem
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private lateinit var ctx: Context
    private lateinit var list: RecyclerView
    private val viewModel: ContactViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = (activity as NewActivity).applicationContext
        bindViews(view)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            createList(it)
        }
        getContacts()
    }

    private fun getContacts(){
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, Array(1) {Manifest.permission.READ_CONTACTS}, 111) }
        } else activity?.let { viewModel.getContacts(it) }
    }


    override fun onRequestPermissionsResult( //TODO: Add this function to the activity, and listen from there
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                viewModel.getContacts(it)
            }
        }
    }

    private fun bindViews(view: View) {
        val binding = FragmentContactsBinding.bind(view)
        list = binding.contactsList
    }

    private fun createList(list: List<ContactItem>) {
        val adapter = ContactsAdapter()
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.VERTICAL
        this.list.layoutManager = lm
        adapter.contactItems = list.toMutableList()
        this.list.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}