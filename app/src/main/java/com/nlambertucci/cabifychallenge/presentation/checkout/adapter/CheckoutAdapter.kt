package com.nlambertucci.cabifychallenge.presentation.checkout.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nlambertucci.cabifychallenge.R
import com.nlambertucci.cabifychallenge.common.isVisible
import com.nlambertucci.cabifychallenge.databinding.CheckoutItemBinding
import com.nlambertucci.cabifychallenge.domain.model.CheckoutProductItem
import java.math.RoundingMode

class CheckoutAdapter(
 private val context: Context,
 private val checkoutItems: List<CheckoutProductItem>
) : RecyclerView.Adapter<CheckoutViewHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHoler {
        return CheckoutViewHoler.newHolder(context,parent)
    }

    override fun getItemCount() = checkoutItems.size

    override fun onBindViewHolder(holder: CheckoutViewHoler, position: Int) {
        holder.onBind(checkoutItems[position], context)
    }
}

class CheckoutViewHoler( private val binding: CheckoutItemBinding): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun onBind(
        checkoutItem: CheckoutProductItem,
        context: Context
    ){
      with(binding){
          checkoutItemTitle.text = checkoutItem.product.name
          checkoutItemTitle.isVisible = true
          checkoutItemPrice.text = "${checkoutItem.currencySymbol} ${checkoutItem.totalPrice}"
          checkoutItemPrice.isVisible = true
          checkoutDiscountTitle.text = context.getString(R.string.checkoutDiscountTitle)
          checkoutDiscountTitle.isVisible = checkoutItem.product.discount != null
          checkoutItemPriceDiscount.text =
              "${checkoutItem.currencySymbol} ${checkoutItem.finalPrice.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()}"
          checkoutItemPriceDiscount.isVisible = checkoutItem.product.discount != null
      }
    }

    companion object {
        fun newHolder(context: Context, parent: ViewGroup): CheckoutViewHoler {
            return CheckoutViewHoler(
                CheckoutItemBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }
    }
}