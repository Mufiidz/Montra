package com.mufidz.montra.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import com.mufidz.montra.screen.MainActivity

object Shortcuts {

    private const val INCOME_ID = "income_id"
    private const val OUTCOME_ID = "outcome_id"
    private const val DISCOUNT_ID = "discount_id"
    private const val PLAN_ID = "plan_id"

    fun setUp(context: Context) {
        context.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                getSystemService(this, ShortcutManager::class.java)?.dynamicShortcuts =
                    getListShortcut()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun Context.getListShortcut() = listOf(
        createShortcut(INCOME_ID, "Income", "https://mufidz.my.id/montra/addReport/income"),
        createShortcut(
            OUTCOME_ID,
            "Outcome",
            "https://mufidz.my.id/montra/addReport/outcome"
        ),
        createShortcut(
            DISCOUNT_ID,
            "Discount Calculator",
            "https://mufidz.my.id/montra/discCal"
        ),
        createShortcut(PLAN_ID, "Money Plan", "https://mufidz.my.id/montra/moneyPlan"),
    ).asReversed()

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun Context.createShortcut(key: String, name: String, deepLink: String) =
        ShortcutInfo.Builder(this, key)
            .setShortLabel(name)
            .setIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    deepLink.toUri(),
                    this,
                    MainActivity::class.java
                )
            )
            .build()
}