package com.celiluysal.ecommerceproductsapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.HomeFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>(), LifecycleObserver {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(){
        (activity as MainActivity).toolbarRightIconVisibility(true)
        (activity as MainActivity).toolbarRightIconDrawable(R.drawable.ic_home)
        (activity as MainActivity).toolbarRightIconClickListener {
            Log.e("HomeFragment", "toolbarRightIconClickListener")
        }

        activity?.lifecycle?.removeObserver(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

//        (activity as MainActivity).toolbarBackIconVisibility(true)
//        (activity as? MainActivity)?.toolbarRightIconClickListener {
//            Log.e("HomeFragment", "toolbarRightIconClickListener")
//        }

        findNavController().navigate(MainNavigationDirections.actionToProductsListFragment(null))

        return binding.root
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

}