package com.mufidz.montra.screen.home.viewholder

import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemProductHomeBinding
import com.mufidz.montra.entity.Product
import com.mufidz.montra.screen.home.ProductAdapter

class ProductHomeViewHolder(private val binding: ItemProductHomeBinding) :
    BaseViewHolder<List<Product>>(binding.root) {
    private val productAdapter by lazy { ProductAdapter() }
    override fun bind(item: List<Product>) {
        productAdapter.setData(item.toMutableList())
        with(binding) {
            listItem.apply {
                adapter = productAdapter
            }
        }
    }


}
