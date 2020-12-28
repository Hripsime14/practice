package com.cypress.myapplication.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.cypress.myapplication.R
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.databinding.ActivityMainBinding
import com.cypress.myapplication.fragments.intro.IntroFragment
import com.cypress.myapplication.fragments.login.LoginFragment
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeActivityFullScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        start()
    }

    private fun start() {
        if (viewModel.getIntroFinished() && viewModel.getLoginFinished()) {
            openActivity()
        } else if (!viewModel.getIntroFinished()) {
            openFragment(IntroFragment.newInstance())
        } else {
            openFragment(LoginFragment.newInstance())
        }
    }

    fun openActivity() {
        startActivity(Intent(this, PracticeActivity::class.java))
        finish()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_main, fragment)
            .commit()
    }

    private fun makeActivityFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}