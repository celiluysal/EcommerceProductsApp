package com.celiluysal.ecommerceproductsapp.ui.login_register.register

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.RegisterFragmentBinding
import com.celiluysal.ecommerceproductsapp.firebase.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.ui.MainActivity

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

        binding.buttonRegister.setOnClickListener {
            if (checkFields()) {
                viewModel.register(
                    RegisterRequestModel(
                        fullName = binding.textInputEditTextFullName.text.toString(),
                        email = binding.textInputEditTextEmail.text.toString(),
                        password = binding.textInputEditTextPassword.text.toString(),
                    )
                )

                viewModel.loadError.observe(viewLifecycleOwner, { loadError ->
                    if (!loadError) {
                        Log.e("RegisterActivity", "login")
                        activity?.let {
                            startActivity(Intent(it, MainActivity::class.java))
                            it.finish()
                        }

                    }
                })
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

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        when {
            binding.textInputEditTextFullName.text.toString().isNullOrBlank() -> {
                toast(getString(R.string.full_name) + " " + getString(R.string.field_cant_be_empty))
                return false
            }
            else -> {
                when {
                    binding.textInputEditTextEmail.text.toString().isNullOrBlank() -> {
                        toast(getString(R.string.email) + " " + getString(R.string.field_cant_be_empty))
                        return false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(binding.textInputEditTextEmail.text.toString())
                        .matches() -> {
                        toast(getString(R.string.wrong_email_type))
                        return false
                    }
                    binding.textInputEditTextPassword.text.toString().isNullOrBlank() -> {
                        toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
                        return false
                    }
                    else -> {
                        when {
                            binding.textInputEditTextPassword.text.toString().length < 6 -> {
                                toast(getString(R.string.short_password))
                                return false
                            }
                            binding.textInputEditTextPassword.text.toString().length > 18 -> {
                                toast(getString(R.string.long_password))
                                return false
                            }
                            binding.textInputEditTextPassword.text.toString() != binding.textInputEditTextPasswordAgain.text.toString() -> {
                                toast(getString(R.string.did_not_match_passwords))
                                return false
                            }
                        }
                    }
                }

                return true
            }
        }

    }

}