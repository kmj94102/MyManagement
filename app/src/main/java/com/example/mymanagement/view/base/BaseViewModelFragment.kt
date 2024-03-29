package com.example.mymanagement.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId : Int
) : Fragment() {

    protected lateinit var binding: B
    abstract val viewModel : VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            setVariable(com.example.mymanagement.BR.vm, viewModel)
        }

        return binding.root
    }

    open fun onBackPress() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

}