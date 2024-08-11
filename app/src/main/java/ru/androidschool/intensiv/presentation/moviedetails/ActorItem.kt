package ru.androidschool.intensiv.presentation.moviedetails

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemActorBinding
import ru.androidschool.intensiv.models.domain.Actor

class ActorItem(private val actor: Actor) : BindableItem<ItemActorBinding>() {

    override fun getLayout(): Int = R.layout.item_actor

    override fun initializeViewBinding(view: View) = ItemActorBinding.bind(view)

    override fun bind(viewBinding: ItemActorBinding, position: Int) {
        viewBinding.actorName.text = actor.name
        Picasso.get()
            .load(actor.photoUrl)
            .placeholder(R.drawable.item_placeholder)
            .into(viewBinding.actorImage)
    }
}
