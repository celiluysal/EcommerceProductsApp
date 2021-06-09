package com.celiluysal.ecommerceproductsapp.ui.login_register.register

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.RegisterFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.utils.isAgainPassword
import com.celiluysal.ecommerceproductsapp.utils.isEmail
import com.celiluysal.ecommerceproductsapp.utils.isEmpty
import com.celiluysal.ecommerceproductsapp.utils.isPassword

class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        initSimpleMessageDialog()

        binding.buttonRegister.setOnClickListener {
            binding.textInputEditTextFullName.isEmpty(context)?.let {
                showMessageDialog(getString(R.string.full_name) + " " + it)
                return@setOnClickListener
            }

            binding.textInputEditTextEmail.isEmail(context)?.let {
                showMessageDialog(getString(R.string.email) + " " + it)
                return@setOnClickListener
            }

            binding.textInputEditTextPassword.isPassword(context)?.let {
                showMessageDialog(getString(R.string.password) + " " + it)
                return@setOnClickListener
            }

            binding.textInputEditTextPasswordAgain.isAgainPassword(
                context,
                binding.textInputEditTextPassword.text.toString()
            )?.let {
                showMessageDialog(getString(R.string.password) + " " + it)
                return@setOnClickListener
            }

            showLoading()
            viewModel.registerAndLogin(
                RegisterRequestModel(
                    fullName = binding.textInputEditTextFullName.text.toString(),
                    email = binding.textInputEditTextEmail.text.toString(),
                    password = binding.textInputEditTextPassword.text.toString(),
                )
            ) { success, error ->
                dismissLoading()
                if (success) {
                    activity?.let {
                        startActivity(Intent(it, MainActivity::class.java))
                        it.finish()
                    }
                } else
                    error?.let { showMessageDialog(it) }
            }
        }

        binding.textViewLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        return binding.root
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): RegisterFragmentBinding {
        return RegisterFragmentBinding.inflate(inflater, container, false)
    }
}