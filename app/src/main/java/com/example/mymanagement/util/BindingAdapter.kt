package com.example.mymanagement.util

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.view.xml.ui.custom.LineChip
import com.example.mymanagement.view.xml.ui.custom.SearchEditTextView

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("submitList")
    fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
        (view.adapter as? ListAdapter<Any, *>)?.submitList(itemList)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("isVisible")
    fun bindIsVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("html")
    fun bindHtmlText(textView: TextView, text: String) {
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter(value=["gridCount", "verticalSpacing", "horizontalSpacing"], requireAll = true)
    fun bindGridDecoration(
        recyclerView: RecyclerView,
        gridCount: Int,
        verticalSpacing: Int,
        horizontalSpacing: Int
    ) {
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = gridCount,
                verticalSpacing = verticalSpacing,
                horizontalSpacing = horizontalSpacing,
                includeEdge = false
            )
        )
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("onImageViewClick")
    fun bindImageViewClick(imageView: ImageView, onClick: () -> Unit) {
        imageView.setOnClickListener{
            onClick()
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("addLineChips")
    fun bindAddLineChips(linearLayout: LinearLayout, lineList: List<String>) {
        linearLayout.removeAllViews()
        lineList.forEach {
            val lineInfo = SubwayLine.getSubwayLineByCode(it)
            linearLayout.addView(
                LineChip(linearLayout.context).apply {
                    text = lineInfo.lineName
                    badgeColor = lineInfo.color.toInt()
                }
            )
        }
    }

}