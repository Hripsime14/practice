package com.cypress.myapplication

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.cypress.myapplication.Util.isEmailValid
import com.cypress.myapplication.databinding.FragmentIntroBinding
import com.cypress.myapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
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
            } else {
                Toast.makeText(context, "Your email is nottt valid", Toast.LENGTH_SHORT).show()

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