package com.celiluysal.ecommerceproductsapp.ui.product_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.ProductDetailFragmentBinding
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class ProductDetailFragment : BaseFragment<ProductDetailFragmentBinding, ProductDetailViewModel>() {

    private val args: ProductDetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        (activity as MainActivity).toolbarBackIconVisibility(true)

        binding.textViewProductName.text = args.product.name
        binding.textViewProductDetail.text = args.product.description
        SessionManager.shared.getCategoryName(args.product.categoryId) { categoryName, error ->
            binding.textViewProductCategory.text = categoryName
        }
        binding.textViewProductUpdateDate.text = args.product.updateDate
        binding.textViewProductPrice.text = "${args.product.price} ₺"

        Glide.with(binding.root).load(args.product.imageUrl)
            .placeholder(R.drawable.place_holder)
            .into(binding.imageViewProduct)

        binding.buttonEdit.setOnClickListener {
            findNavController().navigate(
                ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(
                    args.product
                )
            )
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteProduct(args.product.id) { success, error ->
                if (success)
                    activity?.onBackPressed()

            }

        }



        return binding.root
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProductDetailFragmentBinding {
        return ProductDetailFragmentBinding.inflate(inflater, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).toolbarBackIconVisibility(false)
    }

}