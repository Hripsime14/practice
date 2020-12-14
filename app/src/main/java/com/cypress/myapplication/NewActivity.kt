package com.cypress.myapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.cypress.myapplication.databinding.ActivityNewBinding
import com.cypress.myapplication.fragments.contacts.ContactsFragment
import com.cypress.myapplication.fragments.users.UsersFragment
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityNewBinding
    private lateinit var isGranted :(Boolean) -> Unit

    fun setIsGranted(isGranted:(Boolean) -> Unit) {
        this.isGranted = isGranted
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
                    setTitle("Users")
                    drawerLayout.close()
                }
                R.id.contactsFragment -> {
                    replaceFragment(ContactsFragment.newInstance())
                    setTitle("Contacts")
                    drawerLayout.close()
                }
                R.id.page2Fragment -> {
                    replaceFragment(Page2Fragment.newInstance())
                    setTitle("Page2")
                    drawerLayout.close()
                }
            }
            true
        }

        replaceFragment(UsersFragment.newInstance())

    }

    private fun setTitle(title: String) {
        binding.toolbar.title = title
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setTitle("Users")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
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
        isGranted.invoke(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }
}