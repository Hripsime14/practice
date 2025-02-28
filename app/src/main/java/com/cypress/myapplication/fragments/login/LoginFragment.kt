package com.cypress.myapplication.fragments.login

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.cypress.myapplication.R
import com.cypress.myapplication.util.isEmailValid
import com.cypress.myapplication.databinding.FragmentLoginBinding
import com.cypress.myapplication.activities.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModel()

    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var login: ImageButton
    private lateinit var loginWithFb: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setListeners()
    }

    private fun initViews(view: View) {
        binding = FragmentLoginBinding.bind(view)
        email = binding.emailEdit
        pass = binding.passEdit
        login = binding.loginButton
        loginWithFb = binding.fbImg
    }

    private fun setListeners() {
        login.setOnClickListener {
            if (isEmailValid(email.text.toString())) {
                Toast.makeText(context, "Your email is valid", Toast.LENGTH_SHORT).show()
                viewModel.setLoginFinished()
                (activity as MainActivity).openActivity()
            } else {
                Toast.makeText(context, "Your email is not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}