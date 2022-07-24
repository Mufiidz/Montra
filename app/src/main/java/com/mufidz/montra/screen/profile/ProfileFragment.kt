package com.mufidz.montra.screen.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentProfileBinding
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.viewBinding
import com.mufidz.montra.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, PreferencesViewModel>(R.layout.fragment_profile),
    SharedPreferences.OnSharedPreferenceChangeListener {

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

        PreferenceManager.getDefaultSharedPreferences(binding.root.context)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val themeValues = resources.getStringArray(R.array.mode)
        val theme = sharedPreferences?.getString("themeMode", themeValues[0])
        key?.let {
            if (it == "themeMode") {
                theme?.let { it1 -> viewModel.setTheme(it1, binding.root.context) }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(it)
                .unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}