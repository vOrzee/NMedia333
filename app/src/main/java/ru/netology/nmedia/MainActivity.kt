package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInset(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { posts ->
            binding.main.removeAllViews()
            posts.forEach { post ->
                val cardPostBinding = CardPostBinding.inflate(layoutInflater, binding.main, true)
                bindPost(cardPostBinding, post)
                cardPostBinding.like.setOnClickListener {
                    viewModel.likeById(post.id)
                }
                cardPostBinding.share.setOnClickListener {
                    viewModel.shareById(post.id)
                }
            }
        }
    }

    private fun bindPost(
        binding: CardPostBinding,
        post: Post
    ) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published

            like.setImageResource(
                if (post.likedByMe) {
                    R.drawable.ic_liked_24
                } else {
                    R.drawable.ic_like_24
                }
            )

            likeCount.text = CountCalculator.calculate(post.likes)

            shareCount.text = CountCalculator.calculate(post.shares)

            viewsCount.text = CountCalculator.calculate(post.views)
        }
    }

    private fun applyInset(main: View) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft + systemBars.left,
                v.paddingTop + systemBars.top,
                v.paddingRight + systemBars.right,
                v.paddingBottom + systemBars.bottom
            )
            insets
        }
    }
}