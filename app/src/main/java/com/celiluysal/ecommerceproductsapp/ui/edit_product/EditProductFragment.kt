package com.celiluysal.ecommerceproductsapp.ui.edit_product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.EditProductFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog
import com.celiluysal.ecommerceproductsapp.ui.product_detail.ProductDetailFragmentArgs
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.Utils

class EditProductFragment : BaseFragment<EditProductFragmentBinding, EditProductViewModel>() {

    private var price: Double? = null
    private val args: ProductDetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = EditProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProductViewModel::class.java)

        setFields()
        (activity as MainActivity).toolbarBackIconVisibility(true)

        binding.includeProductFields.buttonSave.setOnClickListener {


            if (checkFields()) {
                val p = Product(
                    id = args.product.id,
                    name = binding.includeProductFields.textInputEditTextProductName.text.toString(),
                    description = binding.includeProductFields.textInputEditTextProductDescription.text.toString(),
                    categoryId = binding.includeProductFields.spinnerCategory.selectedItemPosition.toString(),
                    price = binding.includeProductFields.textInputEditTextProductPrice.text.toString().toDouble(),
                    imageUrl = args.product.imageUrl,
                    updateDate = Utils.shared.dateTimeStamp()
                )

                showLoading()

                viewModel.updateProduct(p) {success, error ->
                    dismissLoading()
                    if (success) {
                        messageDialog = MessageDialog(
                            object : MessageDialog.MessageDialogListener {
                                override fun onLeftButtonClick() {
                                    messageDialog?.dismiss()
                                    binding.includeProductFields.textInputEditTextProductName.text?.clear()
                                    binding.includeProductFields.textInputEditTextProductDescription.text?.clear()
                                    binding.includeProductFields.textInputEditTextProductPrice.text?.clear()
                                    binding.includeProductFields.imageViewProduct.setImageResource(R.drawable.im_take_photo)
                                }
                            })
                        messageDialog?.leftButtonText = getString(R.string.okay)
                        showMessageDialog("Ürün başarıyla eklendi.")
                    }
                    findNavController().navigate(EditProductFragmentDirections.actionEditProductFragmentToProductDetailFragment(p))

                }
            }

        }

        return binding.root
    }

    private fun setFields() {
        binding.includeProductFields.textInputEditTextProductName.setText(args.product.name)
        binding.includeProductFields.textInputEditTextProductDescription.setText(args.product.description)
        setSpinnerCategory(args.product.categoryId)
        binding.includeProductFields.textInputEditTextProductPrice.setText(args.product.price.toString())
        Glide.with(binding.root).load(args.product.imageUrl)
            .placeholder(R.drawable.place_holder)
            .into(binding.includeProductFields.imageViewProduct)
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        when {
//            photo == null -> {
//                toast("alan boş olamaz")
//                return false
//            }
            binding.includeProductFields.textInputEditTextProductName.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            binding.includeProductFields.textInputEditTextProductDescription.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            binding.includeProductFields.spinnerCategory.selectedItem == getString(R.string.category) -> {
                toast("alan boş olamaz")
                return false
            }
            binding.includeProductFields.textInputEditTextProductPrice.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            else -> {
                val priceValue = binding.includeProductFields.textInputEditTextProductPrice.text.toString().toDouble()
                price = priceValue
            }
        }
        return true
    }

    private fun setSpinnerCategory(categoryId: String) {
        SessionManager.shared.getCategoryNameList { categoryNameList ->
            binding.includeProductFields.spinnerCategory.adapter =
                context?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_list_item_1,
                        categoryNameList
                    )
                }
            binding.includeProductFields.spinnerCategory.bringToFront()
            binding.includeProductFields.spinnerCategory.setSelection(categoryId.toInt())
            binding.includeProductFields.spinnerCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (categoryNameList[p2] != getString(R.string.category)) {
                            binding.includeProductFields.textInputEditTextSpinnerCategory.setText(" ")
                            binding.includeProductFields.textInputSpinnerCategory.hint = getString(R.string.category)
                        }
                    }
                }
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): EditProductFragmentBinding {
        return EditProductFragmentBinding.inflate(inflater, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).toolbarBackIconVisibility(true)
    }

}