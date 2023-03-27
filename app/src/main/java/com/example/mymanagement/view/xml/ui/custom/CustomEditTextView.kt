package com.example.mymanagement.view.xml.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.mymanagement.R
import com.example.mymanagement.util.dpToPx
import com.google.android.material.textfield.TextInputEditText

open class CustomEditTextView : TextInputEditText {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle : Int) : super(context, attributeSet, defStyle) {
        initView()
    }

    private fun initView() {
        setBackgroundResource(R.drawable.bg_gray_outline_round6)
        setPadding(getPadding(), getPadding(), getPadding(), getPadding())
        setTextColor(ContextCompat.getColor(context, R.color.black))
        setHintTextColor(ContextCompat.getColor(context, R.color.gray))

        compoundDrawablePadding = dpToPx(context, 16)
        gravity = Gravity.CENTER_VERTICAL

        onFocusChangeListener = OnFocusChangeListener { _, isFocused ->
            when(isFocused) {
                true -> {
                    setBackgroundResource(R.drawable.bg_green_outline_round6)
                }
                false -> {
                    setBackgroundResource(R.drawable.bg_gray_outline_round6)
                }
            }
        }
    }

    private fun getPadding() : Int =
        dpToPx(context, 12)

    @SuppressLint("ClickableViewAccessibility")
    fun setDrawableClickListener(drawablePosition: Int, listener: () -> Unit) {
        setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= (right - compoundDrawables[drawablePosition].bounds.width())) {
                    listener()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

    companion object {
        const val DRAWABLE_START = 0
        const val DRAWABLE_TOP = 1
        const val DRAWABLE_END = 2
        const val DRAWABLE_BOTTOM = 3
    }
}