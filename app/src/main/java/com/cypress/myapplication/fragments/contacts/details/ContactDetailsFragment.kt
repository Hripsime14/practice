package com.cypress.myapplication.fragments.contacts.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.databinding.FragmentContactDetailsBinding
import com.cypress.myapplication.modeldatas.model.ContactItem

class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {
    private var contactItem: ContactItem? = null
    private lateinit var binding: FragmentContactDetailsBinding
    private lateinit var image: ImageView
    private lateinit var fName: EditText
    private lateinit var lName: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactItem = it.getParcelable(CONTACT_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding = FragmentContactDetailsBinding.bind(view)
        bindViews()
        setValues()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindViews() {
        image = binding.imageView
        fName = binding.firstName
        lName = binding.lastName
        phone = binding.phone
        email = binding.email
    }

    private fun setValues() {
        fName.setText(contactItem?.fullName)
        phone.setText(contactItem?.photo)
        email.setText(contactItem?.email)
    }


    //I pursue experimental options=)
    private fun makeFocusable(flag: String) {
        fName.isEnabled = flag.toBoolean()
        fName.isFocusableInTouchMode = flag.toBoolean()
        lName.isEnabled = flag.toBoolean()
        lName.isFocusableInTouchMode = flag.toBoolean()
        phone.isEnabled = flag.toBoolean()
        phone.isFocusableInTouchMode = flag.toBoolean()
        email.isEnabled = flag.toBoolean()
        email.isFocusableInTouchMode = flag.toBoolean()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.overflow_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.editButton -> {
            menu.getItem(1).isVisible = true
            menu.getItem(0).isVisible = false
            makeFocusable("true")
            true
        }
        R.id.saveButton -> {
            menu.getItem(0).isVisible = true
            menu.getItem(1).isVisible = false
            makeFocusable("false")
            contactItem = ContactItem(fName.text.toString(), phone.text.toString(), "", email.text.toString())
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }


    companion object {
        private const val CONTACT_KEY = "contact"
        fun newInstance(contactItem: ContactItem) =
            ContactDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CONTACT_KEY, contactItem)
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        contactItem?.let { (activity as PracticeActivity).setUpdatedContact(it) }
    }
}