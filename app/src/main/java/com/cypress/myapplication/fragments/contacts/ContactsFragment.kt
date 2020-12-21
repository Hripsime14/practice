
package com.cypress.myapplication.fragments.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypress.myapplication.NewActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.databinding.FragmentContactsBinding
import com.cypress.myapplication.fragments.adapters.ContactsAdapter
import com.cypress.myapplication.fragments.contacts.details.ContactDetailsFragment
import com.cypress.myapplication.manager.SwipeToDelete
import com.cypress.myapplication.modeldatas.model.ContactItem
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ContactViewModel by viewModel()
    private lateinit var list: MutableList<ContactItem>
    private var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NewActivity).setTitle("Contacts")
        setHasOptionsMenu(true)
        bindViews(view)
        (activity as NewActivity).setIsContactPermGranted {
            if(it) {
                getContacts()
            } else {
                (activity as NewActivity).supportFragmentManager.popBackStack()
            }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            list = it as MutableList<ContactItem>
            createList(it)
        }
        getContacts()


        (activity as NewActivity).setOnUpdatedContact(object : NewActivity.OnUpdateContact {
            override fun onUpdateContact(contact: ContactItem) {
                list[pos] = contact
                createList(list)
            }
        })
    }

    private fun  getContacts(){
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, Array(1) {Manifest.permission.READ_CONTACTS}, 111) }
        } else activity?.let { viewModel.getContacts(it) }
    }

    private fun bindViews(view: View) {
        val binding = FragmentContactsBinding.bind(view)
        recyclerView = binding.contactsList
    }

    private fun createList(list: List<ContactItem>) {
        val adapter = ContactsAdapter()
        adapter.setOnItemClickListener {
            openDetailsFragment(it)
            pos = list.indexOf(it)
        }
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.VERTICAL
        this.recyclerView.layoutManager = lm
        adapter.contactItems = list.toMutableList()
        this.recyclerView.adapter = adapter
        implementSwipe(adapter)
    }

    private fun openDetailsFragment(contactItem: ContactItem) {
        (activity as NewActivity).replaceFragment(ContactDetailsFragment.newInstance(contactItem))
    }

    private fun implementSwipe(adapter: ContactsAdapter) {
        val item = object: SwipeToDelete(context, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("ooo", "onSwiped: ${viewHolder.adapterPosition}")
                adapter.deleteItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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