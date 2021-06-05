package com.celiluysal.ecommerceproductsapp.ui.product_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.ProductDetailFragmentBinding

class ProductDetailFragment : BaseFragment<ProductDetailFragmentBinding, ProductDetailViewModel>() {

    val args: ProductDetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        binding.textViewProductName.text = args.product.name
        binding.textViewProductDetail.text = args.product.description
        binding.textViewProductCategory.text = args.product.categoryId.toString()
        binding.textViewProductPrice.text = "${args.product.price} ₺"

        Glide.with(binding.root).load(args.product.imageUrl)
            .placeholder(R.drawable.place_holder)
            .into(binding.imageViewProduct)



        return binding.root
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProductDetailFragmentBinding {
        return ProductDetailFragmentBinding.inflate(inflater, container, false)
    }

}