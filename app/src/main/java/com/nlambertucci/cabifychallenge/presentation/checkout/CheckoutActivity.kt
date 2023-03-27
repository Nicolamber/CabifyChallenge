package com.nlambertucci.cabifychallenge.presentation.checkout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlambertucci.cabifychallenge.R
import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.common.isVisible
import com.nlambertucci.cabifychallenge.databinding.ActivityCheckoutBinding
import com.nlambertucci.cabifychallenge.domain.model.CheckoutProductItem
import com.nlambertucci.cabifychallenge.presentation.checkout.adapter.CheckoutAdapter
import com.nlambertucci.cabifychallenge.presentation.checkout.viewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode


@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.checkoutLiveStatus.observe(this) { status ->
            when (status) {
                is CheckoutViewModel.CheckoutStatus.Loading -> {
                    binding.checkoutLoadingIndicator.isVisible = true
                }
                is CheckoutViewModel.CheckoutStatus.Success -> initView(
                    status.products,
                    status.total
                )
                is CheckoutViewModel.CheckoutStatus.Error -> handleErrorMessage(status.errorMessage)
            }
        }
        viewModel.getShoppingList()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(products: List<CheckoutProductItem>, total: Double) {
        binding.checkoutTitle.isVisible = true
        binding.checkoutList.apply {
            isVisible = true
            setHasFixedSize(true)
            adapter = CheckoutAdapter(context, products)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.checkoutTotalTitle.isVisible = total > 0
        binding.totalPrice.text =
            "${Constants.CURRENCY_SYMBOL} ${
                total.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
            }"
        binding.totalPrice.isVisible = total > 0
        binding.confirmPayment.apply {
            isVisible = true
            text = context.getString(R.string.confirmPaymentTitle)
            setOnClickListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.successPayment),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        binding.checkoutLoadingIndicator.isVisible = false
    }

    private fun handleErrorMessage(errorMessage: String?) {
        Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, CheckoutActivity::class.java)
        }
    }
}