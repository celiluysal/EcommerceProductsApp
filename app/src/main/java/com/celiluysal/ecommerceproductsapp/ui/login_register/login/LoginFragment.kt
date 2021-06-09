package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.LoginFragmentBinding
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.utils.isEmail
import com.celiluysal.ecommerceproductsapp.utils.isPassword

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        initSimpleMessageDialog()

        binding.buttonLogin.setOnClickListener {
            binding.editTextEmail.isEmail(context)?.let {
                showMessageDialog(getString(R.string.email) + " " + it)
                return@setOnClickListener
            }

            binding.editTextPassword.isPassword(context)?.let {
                showMessageDialog(getString(R.string.password) + " " + it)
                return@setOnClickListener
            }

            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            showLoading()
            viewModel.login(email, password) { success, error ->
                dismissLoading()
                if (success) {
                    activity?.let {
                        it.startActivity(Intent(it, MainActivity::class.java))
                        it.finish()
                    }
                } else
                    error?.let { showMessageDialog(it) }
            }
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        return binding.root
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(inflater, container, false)
    }

}