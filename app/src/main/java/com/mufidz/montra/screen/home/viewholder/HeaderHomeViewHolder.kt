package com.mufidz.montra.screen.home.viewholder

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseViewHolder
import com.mufidz.montra.databinding.ItemHeaderHomeBinding
import com.mufidz.montra.utils.slideLeftRightAnim

class HeaderHomeViewHolder(private val binding: ItemHeaderHomeBinding) :
    BaseViewHolder<String>(binding.root) {
    override fun bind(item: String) {
        binding.apply {
            root.visibility = if (item.isNotEmpty()) View.VISIBLE else View.GONE
            txtNameHeader.text = item
            btnProfile.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.profileFragment, null, navOptions { slideLeftRightAnim() })
            }
        }
    }
}
