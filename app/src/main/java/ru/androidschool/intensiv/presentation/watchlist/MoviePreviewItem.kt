package ru.androidschool.intensiv.presentation.watchlist

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.ItemMovieSmallBinding
import ru.androidschool.intensiv.utils.loadImage

class MoviePreviewItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemMovieSmallBinding>() {

    override fun getLayout() = R.layout.item_movie_small

    override fun bind(view: ItemMovieSmallBinding, position: Int) {
        view.imagePreview.setOnClickListener {
            onClick.invoke(content)
        }
        view.imagePreview.loadImage(
            imageUrl = content.imageUrl,
            placeholderResId = R.drawable.item_placeholder
        )
    }

    override fun initializeViewBinding(v: View) = ItemMovieSmallBinding.bind(v)

    override fun getId(): Long =
        content.id.toLong()

    override fun hasSameContentAs(other: Item<*>): Boolean =
        (content == (other as MoviePreviewItem).content)
}
