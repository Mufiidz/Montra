package com.mufidz.montra.screen.profile

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mufidz.montra.R
import com.mufidz.montra.utils.slideLeftRightAnim

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<Preference>("tag")?.let {
            it.setOnPreferenceClickListener {
                findNavController().navigate(
                    R.id.tagProfileFragment,
                    null,
                    navOptions { slideLeftRightAnim() })
                true
            }
        }
    }
}