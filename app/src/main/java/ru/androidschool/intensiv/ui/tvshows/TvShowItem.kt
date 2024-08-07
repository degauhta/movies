package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.ItemTvShowBinding

class TvShowItem(
    private val content: TvShow,
    private val onClick: (movie: TvShow) -> Unit
) : BindableItem<ItemTvShowBinding>() {

    override fun getLayout(): Int = R.layout.item_tv_show

    override fun initializeViewBinding(view: View) = ItemTvShowBinding.bind(view)

    override fun bind(viewBinding: ItemTvShowBinding, position: Int) {
        viewBinding.description.text = content.title
        viewBinding.movieRating.rating = content.rating
        viewBinding.content.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(content.imageUrl)
            .into(viewBinding.imagePreview)
    }
}
