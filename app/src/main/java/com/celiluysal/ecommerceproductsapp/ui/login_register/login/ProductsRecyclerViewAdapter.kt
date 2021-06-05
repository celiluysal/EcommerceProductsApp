package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.celiluysal.ecommerceproductsapp.databinding.ItemProductCardBinding
import com.celiluysal.ecommerceproductsapp.models.Product

class ProductsRecyclerViewAdapter(val products: MutableList<Product>, val clickListener: ProductAdapterClickListener):
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(product: Product, action: ProductAdapterClickListener) {

                binding.textViewProductName.text = product.name
                binding.textViewProductPrice.text = product.price.toString()


                itemView.setOnClickListener {
                    action.onProductCardClick(product, adapterPosition)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], clickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface ProductAdapterClickListener {
        fun onProductCardClick(item: Product, position: Int)
    }
}