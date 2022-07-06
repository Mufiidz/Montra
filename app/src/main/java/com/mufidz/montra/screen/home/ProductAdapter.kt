package com.mufidz.montra.screen.home

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseAdapter
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.base.ItemListener
import com.mufidz.montra.databinding.ItemProductBinding
import com.mufidz.montra.entity.Product
import com.mufidz.montra.utils.inflate
import com.mufidz.montra.utils.slideLeftRightAnim
import com.mufidz.montra.utils.snackbar

class ProductAdapter : BaseAdapter<Product, ItemListener<Product>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(ItemProductBinding.bind(parent.inflate(R.layout.item_product)))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            with(binding) {
                txtTitleProduct.text = item.title
                root.setOnClickListener {
                    val destination = item.destination
                    if (destination != null) {
                        it.findNavController()
                            .navigate(destination, item.bundle, navOptions { slideLeftRightAnim() })
                    } else {
                        it.snackbar("Coming Soon")
                    }
                }
            }
        }
    }
}