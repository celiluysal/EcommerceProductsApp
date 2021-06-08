package com.celiluysal.ecommerceproductsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.databinding.ItemCategoryCardBinding
import com.celiluysal.ecommerceproductsapp.databinding.ItemProductCardBinding
import com.celiluysal.ecommerceproductsapp.models.Category

class CategoriesRecyclerViewAdapter(
    private val categories: List<Category>,
    private val clickListener: CategoryAdapterClickListener
) :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, action: CategoryAdapterClickListener) {

            binding.textViewProductName.text = category.name

            Glide.with(binding.root).load(category.imageUrl)
                .placeholder(R.drawable.place_holder)
                .into(binding.imageViewCategory)

            itemView.setOnClickListener {
                action.onCategoryCardClick(category, adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], clickListener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface CategoryAdapterClickListener {
        fun onCategoryCardClick(item: Category, position: Int)
    }
}