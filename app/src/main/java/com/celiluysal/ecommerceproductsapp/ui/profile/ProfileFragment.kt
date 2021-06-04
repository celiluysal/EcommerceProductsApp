package com.celiluysal.ecommerceproductsapp.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.ProfileFragmentBinding
import com.celiluysal.ecommerceproductsapp.ui.login_register.LoginRegisterActivity

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        observeViewModel()

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            activity?.let {
                it.startActivity(Intent(it, LoginRegisterActivity::class.java))
                it.finish()
            }
        }

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner, { user ->
            binding.textViewFullName.text = user.fullName
            binding.textViewEmail.text = user.email
        })
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfileFragmentBinding {
        return ProfileFragmentBinding.inflate(inflater, container, false)
    }

}