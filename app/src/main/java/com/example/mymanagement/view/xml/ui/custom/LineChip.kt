package com.example.mymanagement.view.xml.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.mymanagement.R
import com.example.mymanagement.util.dpToPx

class LineChip constructor(
    context : Context,
    attrs : AttributeSet?= null
) : AppCompatTextView(context, attrs) {

    var badgeColor = Color.LTGRAY
        set(value) {
            field = value
            invalidate()
        }

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val verticalPadding = dpToPx(context, 2)
        val horizontalPadding = dpToPx(context, 7)

        setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )

        setTextColor(ContextCompat.getColor(context, R.color.white))
        textSize = 12f
        typeface = Typeface.DEFAULT_BOLD
        background = null

        layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = dpToPx(context, 5)
            }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            height / 2f,
            height / 2f,
            paint.apply { color = badgeColor }
        )

        super.onDraw(canvas)
    }

}