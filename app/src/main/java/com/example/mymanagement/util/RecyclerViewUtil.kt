package com.example.mymanagement.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val verticalSpacing: Int, private val horizontalSpacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * horizontalSpacing / spanCount
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount

        if (position < spanCount) {
            outRect.top = verticalSpacing
        }
        outRect.bottom = verticalSpacing

        if (includeEdge) {
            if (column == 0) {
                outRect.left = horizontalSpacing
            }
            if (column == spanCount - 1) {
                outRect.right = horizontalSpacing
            }
            if (position < spanCount) {
                outRect.top = verticalSpacing
            }
        }
    }
}