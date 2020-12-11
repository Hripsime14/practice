package com.cypress.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.cypress.myapplication.databinding.ActivityNewBinding
import com.cypress.myapplication.fragments.users.UsersFragment
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityNewBinding
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

        navView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.usersFragment -> {
                    replaceFragment(UsersFragment.newInstance())
                    setTitle("Users")
                    drawerLayout.close()
                }
                R.id.page1Fragment -> {
                    replaceFragment(Page1Fragment.newInstance())
                    setTitle("Page1")
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
        setTitle("Users") // TODO: check why not changing the title
    }

    private fun setTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


    private fun replaceFragment(fragment: Fragment) {
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
}