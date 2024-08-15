package ru.androidschool.intensiv.presentation.moviedetails

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemActorBinding
import ru.androidschool.intensiv.models.domain.Actor
import ru.androidschool.intensiv.utils.loadImage

class ActorItem(private val actor: Actor) : BindableItem<ItemActorBinding>() {

    override fun getLayout(): Int = R.layout.item_actor

    override fun initializeViewBinding(view: View) = ItemActorBinding.bind(view)

    override fun bind(viewBinding: ItemActorBinding, position: Int) {
        viewBinding.actorName.text = actor.name
        viewBinding.actorImage.loadImage(
            imageUrl = actor.photoUrl,
            placeholderResId = R.drawable.item_placeholder
        )
    }
}
