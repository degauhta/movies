package ru.androidschool.intensiv.presentation.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.ItemTvShowBinding

class TvShowItem(
    private val movie: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemTvShowBinding>() {

    override fun getLayout(): Int = R.layout.item_tv_show

    override fun initializeViewBinding(view: View) = ItemTvShowBinding.bind(view)

    override fun bind(viewBinding: ItemTvShowBinding, position: Int) {
        viewBinding.description.text = movie.title
        viewBinding.movieRating.rating = movie.rating
        viewBinding.content.setOnClickListener {
            onClick.invoke(movie)
        }

        Picasso.get()
            .load(movie.imageUrl)
            .into(viewBinding.imagePreview)
    }
}
