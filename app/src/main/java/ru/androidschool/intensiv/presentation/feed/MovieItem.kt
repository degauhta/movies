package ru.androidschool.intensiv.presentation.feed

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.ItemMovieBinding

class MovieItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemMovieBinding>() {

    override fun getLayout(): Int = R.layout.item_movie

    override fun bind(view: ItemMovieBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = ItemMovieBinding.bind(v)
}
