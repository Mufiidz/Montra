package com.mufidz.montra.screen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseActivity
import com.mufidz.montra.databinding.ActivityMainBinding
import com.mufidz.montra.utils.Shortcuts
import com.mufidz.montra.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel by viewModels<PreferencesViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        Shortcuts.setUp(applicationContext)
        val theme = PreferenceManager.getDefaultSharedPreferences(context).getString("themeMode", "")
        theme?.let { viewModel.setTheme(it, this) }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}