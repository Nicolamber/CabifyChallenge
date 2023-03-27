package com.nlambertucci.cabifychallenge.presentation.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlambertucci.cabifychallenge.R
import com.nlambertucci.cabifychallenge.common.Constants.ACTION_ADD
import com.nlambertucci.cabifychallenge.common.Constants.ACTION_REMOVE
import com.nlambertucci.cabifychallenge.common.isVisible
import com.nlambertucci.cabifychallenge.databinding.FragmentProductsListBinding
import com.nlambertucci.cabifychallenge.domain.model.Product
import com.nlambertucci.cabifychallenge.presentation.products.adapter.ProductItemAdapter
import com.nlambertucci.cabifychallenge.presentation.products.viewmodel.ProductsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private val binding: FragmentProductsListBinding by lazy {
        FragmentProductsListBinding.inflate(
            layoutInflater,
            requireActivity().findViewById(R.id.cabify_fragment_container),
            false
        )
    }

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.liveStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ProductsListViewModel.Status.Loading -> {
                    binding.loadingIndicator.isVisible = true
                }
                is ProductsListViewModel.Status.Success -> {
                    initView(status.products)
                }
                is ProductsListViewModel.Status.Error -> {
                    handleErrorMessage(status.errorMessage)
                }
                is ProductsListViewModel.Status.Redirect -> {
                    handleRedirection(status.intent)
                }
            }

        }

        viewModel.fetchProducts()
    }

    private fun initView(products: List<Product>) {
        binding.productListTitle.isVisible = true
        binding.productList.apply {
            isVisible = true
            setHasFixedSize(true)
            adapter = ProductItemAdapter(
                products,
                requireContext()
            ) {
                when (it.action) {
                    ACTION_ADD -> onProductAdded(it.productSelected)
                    ACTION_REMOVE -> onProductRemoved(it.productSelected)
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.initCheckoutFlow.apply {
            isVisible = true
            isEnabled = true
            text = context.getString(R.string.initCheckoutFlowTitle)
            setOnClickListener {
                viewModel.initCheckoutFlow(context)
            }
        }
        binding.loadingIndicator.isVisible = false
    }

    private fun onProductAdded(product: Product) {
        viewModel.addItemToCart(product)
    }

    private fun onProductRemoved(product: Product) {
        viewModel.removeItemFromCart(product)
    }

    private fun handleRedirection(intent: Intent) {
        startActivity(intent)
    }

    private fun handleErrorMessage(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = ProductsListFragment()
    }
}