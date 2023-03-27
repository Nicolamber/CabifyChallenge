package com.nlambertucci.cabifychallenge.presentation.products.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.common.Constants.ACTION_ADD
import com.nlambertucci.cabifychallenge.common.Constants.ACTION_REMOVE
import com.nlambertucci.cabifychallenge.common.isVisible
import com.nlambertucci.cabifychallenge.databinding.ProductItemBinding
import com.nlambertucci.cabifychallenge.domain.model.Product
import com.nlambertucci.cabifychallenge.domain.model.ProductItemDto

class ProductItemAdapter(
    private val products: List<Product>,
    private val context: Context,
    private val onItemSelected: (ProductItemDto) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.newHolder(context, parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(products[position], context, onItemSelected)
    }

}

class ProductViewHolder(private val binding: ProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var itemsAdded: Int = 0

    @SuppressLint("SetTextI18n")
    fun onBind(
        productItem: Product,
        context: Context,
        onItemSelected: (ProductItemDto) -> Unit
    ) {
        Glide.with(context)
            .load(productItem.image)
            .into(binding.itemImage)
        binding.itemImage.isVisible = true

        binding.itemTitle.apply {
            text = productItem.name
            isVisible = true
        }
        binding.itemPrice.apply {
            text = "${Constants.CURRENCY_SYMBOL} ${productItem.price}"
            isVisible = true
        }

        binding.discountBadge.apply {
            isVisible = productItem.discount != null
            text = "En Promoción"
        }

        binding.addToCart.apply {
            isVisible = true
            setOnClickListener {
                increaseCounter()
                onItemSelected(ProductItemDto(ACTION_ADD, productItem))
            }

        }

        binding.removeFromCart.apply {
            isVisible = true
            isEnabled = itemsAdded >= 0
            setOnClickListener {
                decreaseCounter()
                onItemSelected(ProductItemDto(ACTION_REMOVE, productItem))
            }
        }

        binding.itemCounter.apply {
            text = "0"
            isVisible = true
        }
    }

    private fun increaseCounter() {
        itemsAdded += 1
        binding.itemCounter.text = itemsAdded.toString()
    }

    private fun decreaseCounter() {
        itemsAdded -= 1
        binding.itemCounter.text = itemsAdded.toString()
    }

    companion object {
        fun newHolder(context: Context, parent: ViewGroup): ProductViewHolder {
            return ProductViewHolder(
                ProductItemBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }
    }
}

