package com.example.kodeemployees.presentation.screens.users.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс, описывающий расстояния между элементами Linear RecyclerView:
 * - space: расстояние между элементами внутри списка
 */
class UsersItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = parent.adapter?.itemCount ?: return
        val adapterLastIndex = itemCount - 1

        val itemPosition = parent.getChildAdapterPosition(view).let {
            if (it == RecyclerView.NO_POSITION) return else it
        }

        val divider = space / 2

        outRect.top = if (itemPosition == 0) 0 else divider
        outRect.bottom = if (itemPosition == adapterLastIndex) 0 else divider
    }
}