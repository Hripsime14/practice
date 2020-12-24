package com.cypress.myapplication.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.cypress.myapplication.R
import com.cypress.myapplication.databinding.ActivityPracticeBinding
import com.cypress.myapplication.fragments.contacts.ContactsFragment
import com.cypress.myapplication.fragments.media.MediaFragment
import com.cypress.myapplication.fragments.users.UsersFragment
import com.cypress.myapplication.modeldatas.model.ContactItem
import com.cypress.myapplication.services.OPEN_MEDIA_ACTION
import com.google.android.material.navigation.NavigationView

class PracticeActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nav: NavigationView
    private lateinit var binding: ActivityPracticeBinding
    private lateinit var isContactsPermGranted :(Boolean) -> Unit
    private lateinit var isMediasPermGranted :(Boolean) -> Unit
    private lateinit var onUpdateContact: OnUpdateContact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        setListeners()
        setTitle("Users")
        checkIntentAction()
    }

    private fun initViews() {
        nav = binding.navView
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.syncState()
    }

    private fun setListeners() {
        drawerLayout.addDrawerListener(toggle)
        nav.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.usersFragment -> {
                    replaceFragment(UsersFragment.newInstance())
                }
                R.id.contactsFragment -> {
                    replaceFragment(ContactsFragment.newInstance())
                }
                R.id.mediaFragment -> {
                    if(supportFragmentManager.findFragmentById(R.id.fid) !is MediaFragment) {
                        replaceFragment(MediaFragment.newInstance())
                    }
                }
            }
            drawerLayout.close()
            true
        }
    }

    fun setTitle(title: String) {
        binding.toolbar.title = title
        supportActionBar?.title = title
    }

    private fun checkIntentAction() {
        if (intent.action != null && intent.action == OPEN_MEDIA_ACTION) {
            replaceFragment(MediaFragment.newInstance())
        }
        else {
            replaceFragment(UsersFragment.newInstance())
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fid, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fid, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun setIsContactPermGranted(isGranted:(Boolean) -> Unit) {
        this.isContactsPermGranted = isGranted
    }

    fun setIsMediaPermGranted(isGranted:(Boolean) -> Unit) {
        this.isMediasPermGranted = isGranted
    }

    interface OnUpdateContact {
        fun onUpdateContact(contact: ContactItem)
    }

    fun setUpdatedContact(contact: ContactItem) {
        onUpdateContact.onUpdateContact(contact)
    }

    fun setOnUpdatedContact(onUpdateContact: OnUpdateContact) {
        this.onUpdateContact = onUpdateContact
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        setTitle("Users")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111) {
            isContactsPermGranted.invoke(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
        else if (requestCode == 222) {
            isMediasPermGranted.invoke(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }
}