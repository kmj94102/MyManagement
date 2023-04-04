package com.example.mymanagement.view.xml.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.mymanagement.R
import com.example.mymanagement.util.dpToPx


class DashedDividerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val mPaint: Paint = Paint()
    private var orientation = 0
    private var dashGap: Float = 5f
        get() {
            return dpToPx(context, field.toInt()).toFloat()
        }
    private var dashLength: Float = 5f
        get() {
            return dpToPx(context, field.toInt()).toFloat()
        }
    private var dashThickness: Float = 5f
        get() {
            return dpToPx(context, field.toInt()).toFloat()
        }
    private var color: Int = R.color.gray

    init {
        val typeArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.DashedDividerView, 0, 0)
        try {
            orientation =
                typeArray.getInt(
                    R.styleable.DashedDividerView_dashOrientation,
                    ORIENTATION_HORIZONTAL
                )
            dashGap = typeArray.getFloat(R.styleable.DashedDividerView_dashGap, 5f)
            dashLength = typeArray.getFloat(R.styleable.DashedDividerView_dashLength, 5f)
            dashThickness = typeArray.getFloat(R.styleable.DashedDividerView_dashThickness, 5f)
        } finally {
            typeArray.recycle()
        }

        mPaint.isAntiAlias = true
        mPaint.color = ContextCompat.getColor(context, color)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dashThickness
        mPaint.pathEffect = DashPathEffect(
            floatArrayOf(dashLength, dashGap),
            0f
        )
    }

    constructor(context: Context) : this(context, null) {}

    override fun onDraw(canvas: Canvas) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            val center = height * .5f
            canvas.drawLine(0f, center, width.toFloat(), center, mPaint)
        } else {
            val center = width * .5f
            canvas.drawLine(center, 0f, center, height.toFloat(), mPaint)
        }
    }

    fun setOrientation(orientation: Int) {
        this.orientation = orientation
    }

    fun setDashGap(dashGap: Float) {
        this.dashGap = dashGap
    }

    fun setDashLength(dashLength: Float) {
        this.dashLength = dashLength
    }

    fun setDashThickness(dashThickness: Float) {
        this.dashThickness = dashThickness
    }

    companion object {
        var ORIENTATION_HORIZONTAL = 0
        var ORIENTATION_VERTICAL = 1
    }
}