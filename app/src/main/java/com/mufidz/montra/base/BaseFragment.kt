package com.mufidz.montra.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.mufidz.montra.utils.slideLeftRightAnim
import com.mufidz.montra.utils.snackbar

abstract class BaseFragment<T : ViewBinding, VM : ViewModel>(layoutId: Int) : Fragment(layoutId) {

    protected abstract val viewModel: VM

    protected abstract val binding: T

    //private lateinit var dialogLoading: DialogLoading

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dialogLoading = DialogLoading(binding.root.context)
    }

    protected fun snackbar(message: String, isError: Boolean, txtAction: String? = null, action: View.OnClickListener? = null) {
        binding.root.snackbar(message, isError, txtAction, action)
    }

    protected fun intent(id: Int, args: Bundle? = null) {
        findNavController().navigate(id, args, navOptions { slideLeftRightAnim() })
    }

//    protected fun loading(isLoading: Boolean, message: String? = null) {
//        dialogLoading.show(isLoading,message)
//    }

}