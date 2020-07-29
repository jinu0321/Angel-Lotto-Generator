package com.jincal.valorantstory

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecoration(var divWidth: Int, var divHeight: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = divHeight
        outRect.bottom = divHeight
        outRect.left = divWidth
        outRect.right = divWidth
    }
}