package ru.netology.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.CountCalculator
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            like.isChecked = post.likedByMe

            like.text = CountCalculator.calculate(post.likes)

            share.text = CountCalculator.calculate(post.shares)

            views.text = CountCalculator.calculate(post.views)

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            post.video?.let { videoUrl ->
                videoCard.visibility = View.VISIBLE
                videoCard.setOnClickListener {
                    openVideo(videoUrl)
                }
                playButton.setOnClickListener {
                    openVideo(videoUrl)
                }
            } ?: run {
                videoCard.visibility = View.GONE
            }
        }
    }

    private fun openVideo(videoUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        itemView.context.startActivity(intent)
    }
}