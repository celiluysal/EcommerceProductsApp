package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.LoginFragmentBinding
import com.celiluysal.ecommerceproductsapp.ui.MainActivity

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            if (checkFields()) {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                viewModel.login(email, password)
                viewModel.loggedIn.observe(viewLifecycleOwner, { loggedIn ->
                    if (loggedIn) {
                        activity?.let {
                            it.startActivity(Intent(it, MainActivity::class.java))
                            it.finish()
                        }
                    }
                })
            }
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        if (binding.editTextEmail.text.toString().isNullOrBlank()) {
            toast(getString(R.string.email) + " " + getString(R.string.field_cant_be_empty))
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()){
            toast(getString(R.string.wrong_email_type))
            return false
        }

        if (binding.editTextPassword.text.toString().isNullOrBlank()) {
            toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
            return false
        }
        else {
            if (binding.editTextPassword.text.toString().length < 6){
                toast(getString(R.string.short_password))
                return false
            }
            else if (binding.editTextPassword.text.toString().length > 18){
                toast(getString(R.string.long_password))
                return false
            }
        }
        return true
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(inflater, container, false)
    }

}