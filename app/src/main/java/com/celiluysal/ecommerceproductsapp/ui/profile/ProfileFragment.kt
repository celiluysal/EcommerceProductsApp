package com.celiluysal.ecommerceproductsapp.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        observeLoading(viewLifecycleOwner)
        observeErrorMessage(viewLifecycleOwner)
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

    override fun observeViewModel() {
        super.observeViewModel()
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