package ru.androidschool.intensiv.presentation.watchlist

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.ItemMovieSmallBinding

class MoviePreviewItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemMovieSmallBinding>() {

    override fun getLayout() = R.layout.item_movie_small

    override fun bind(view: ItemMovieSmallBinding, position: Int) {
        view.imagePreview.setOnClickListener {
            onClick.invoke(content)
        }
        // TODO Получать из модели
        Picasso.get()
            .load("https://www.kinopoisk.ru/images/film_big/1143242.jpg")
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = ItemMovieSmallBinding.bind(v)
}
