package com.example.mymanagement.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mymanagement.BR

abstract class BindingActivity<B: ViewDataBinding> constructor(
    @LayoutRes private val layoutId: Int
): AppCompatActivity() {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)

        with(binding) {
            lifecycleOwner = this@BindingActivity
        }

    }

}