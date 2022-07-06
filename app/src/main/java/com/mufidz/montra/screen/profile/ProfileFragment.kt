package com.mufidz.montra.screen.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentProfileBinding
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.viewBinding
import com.mufidz.montra.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, PreferencesViewModel>(R.layout.fragment_profile){

    override val viewModel: PreferencesViewModel by viewModels()

    override val binding: FragmentProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.apply {
                initToolbar(this@ProfileFragment)
            }
            childFragmentManager.beginTransaction().replace(frameSettings.id, SettingsFragment())
                .commit()
        }
    }
}