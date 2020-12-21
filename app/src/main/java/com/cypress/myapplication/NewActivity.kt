package com.cypress.myapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.cypress.myapplication.databinding.ActivityNewBinding
import com.cypress.myapplication.fragments.contacts.ContactsFragment
import com.cypress.myapplication.fragments.media.MediaFragment
import com.cypress.myapplication.fragments.users.UsersFragment
import com.cypress.myapplication.modeldatas.model.ContactItem
import com.cypress.myapplication.services.OPEN_MEDIA_ACTION
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityNewBinding
    private lateinit var isContactsPermGranted :(Boolean) -> Unit
    private lateinit var isMediasPermGranted :(Boolean) -> Unit
//                        private lateinit var  : (contact: ContactItem) ->Unit

    fun setIsContactPermGranted(isGranted:(Boolean) -> Unit) {
        this.isContactsPermGranted = isGranted
    }

    fun setIsMediaPermGranted(isGranted:(Boolean) -> Unit) {
        this.isMediasPermGranted = isGranted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Users")

        navView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.usersFragment -> {
                    replaceFragment(UsersFragment.newInstance())
                    drawerLayout.close()
                }
                R.id.contactsFragment -> {
                    replaceFragment(ContactsFragment.newInstance())
                    drawerLayout.close()
                }
                R.id.mediaFragment -> {
                    if(supportFragmentManager.findFragmentById(R.id.fid) !is MediaFragment) {
                            replaceFragment(MediaFragment.newInstance())
                    }
                    drawerLayout.close()
                }
            }
            true
        }

        if (intent.action != null && intent.action == OPEN_MEDIA_ACTION) {
            replaceFragment(MediaFragment.newInstance())
        }
        else {
            replaceFragment(UsersFragment.newInstance())
        }

    }

    fun setTitle(title: String) {
        binding.toolbar.title = title
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setTitle("Users")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
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

    fun setUpdatedContact(contact: ContactItem) {
        Log.d("zzzzzz", "setUpdatedContact: ${contact.fullName}")
        onUpdateContact.onUpdateContact(contact)
    }

    interface OnUpdateContact {
        fun onUpdateContact(contact: ContactItem)
    }

    fun setOnUpdatedContact(onUpdateContact: OnUpdateContact) {
        this.onUpdateContact = onUpdateContact
    }
    lateinit var onUpdateContact: OnUpdateContact

}