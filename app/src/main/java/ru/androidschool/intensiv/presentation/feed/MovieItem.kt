package ru.androidschool.intensiv.presentation.feed

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.ItemMovieBinding

class MovieItem(
    private val movie: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemMovieBinding>() {

    override fun getLayout(): Int = R.layout.item_movie

    override fun bind(view: ItemMovieBinding, position: Int) {
        view.title.text = movie.title
        view.rating.rating = movie.voteAverage
        view.content.setOnClickListener {
            onClick.invoke(movie)
        }
        Picasso.get()
            .load(movie.imageUrl)
            .placeholder(R.drawable.ic_profile)
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = ItemMovieBinding.bind(v)
}
