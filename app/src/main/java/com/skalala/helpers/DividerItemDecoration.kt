package com.skalala.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.view.View


/**
 * Created by diegogalindo on 3/18/17.
 *
 */
class DividerItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {


    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private var mDivider: Drawable

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, mDivider.intrinsicHeight)
    }

}