package com.example.mymanagement.view.xml.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import com.example.mymanagement.R
import com.example.mymanagement.util.hideKeyBoard

class SearchEditTextView : CustomEditTextView {

    private var listener: (String) -> Unit = {}

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        setBackgroundResource(R.drawable.bg_black_outline_round6)
        maxLines = 1
        isSingleLine = true
        imeOptions = EditorInfo.IME_ACTION_SEARCH
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_search
            ), null, null, null
        )

        onFocusChangeListener = OnFocusChangeListener { _, isFocused ->
            when (isFocused) {
                true -> {
                    setBackgroundResource(R.drawable.bg_green_outline_round6)
                }
                false -> {
                    setBackgroundResource(R.drawable.bg_black_outline_round6)
                }
            }
        }

        setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                listener.invoke(text.toString())
                hideKeyBoard()
                true
            } else {
                false
            }
        }

        setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX <= (left + compoundDrawables[0].bounds.width())) {
                    listener(text.toString())
                    hideKeyBoard()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

    fun setSearchListener(listener: (String) -> Unit) {
        this.listener = listener
    }

}