package com.mufidz.montra.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.mufidz.montra.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun NavOptionsBuilder.slideLeftRightAnim() {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}

fun View.snackbar(
    message: CharSequence,
    isError: Boolean = false,
    txtAction: String? = null,
    action: View.OnClickListener? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .apply {
            if (isError) {
                setBackgroundTint(ContextCompat.getColor(context, R.color.error))
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setActionTextColor(ContextCompat.getColor(context, R.color.white))
            }
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }
    snackbar.setAction(txtAction, action)
    return snackbar.show()
}

fun TextInputLayout.setErrorNot(isError: Boolean, errorMsg: String = "Tidak boleh kosong") {
    isErrorEnabled = isError
    error = if (isError) errorMsg else null
}

fun Toolbar.initToolbar(fragment: Fragment) {
    (fragment.requireActivity() as AppCompatActivity).also {
        it.setSupportActionBar(this)
    }
    fragment.findNavController().apply {
        AppBarConfiguration(graph).also {
            setupWithNavController(this, it)
        }
    }
}

fun EditText.formattedEditText() {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            removeTextChangedListener(this)
            try {
                var originalString = p0.toString()
                if (originalString.contains(",")) {
                    originalString = originalString.replace(",".toRegex(), "")
                }
                val longval = originalString.toLong()
                val formatter = NumberFormat.getInstance(Locale.getDefault()) as DecimalFormat
                formatter.applyPattern("#,###,###,###")
                val formattedString = formatter.format(longval)

                setText(formattedString)
                setSelection(text.length)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
            addTextChangedListener(this)
        }
    })
}