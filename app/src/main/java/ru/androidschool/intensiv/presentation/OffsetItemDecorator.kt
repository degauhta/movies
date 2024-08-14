package ru.androidschool.intensiv.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.androidschool.intensiv.R

class OffsetItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)
        val orientation =
            (parent.layoutManager as? LinearLayoutManager)?.orientation ?: RecyclerView.VERTICAL

        if (itemPosition > 0 && orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left =
                view.context.resources.getDimensionPixelOffset(R.dimen.material_margin_normal)
        }
    }
}
