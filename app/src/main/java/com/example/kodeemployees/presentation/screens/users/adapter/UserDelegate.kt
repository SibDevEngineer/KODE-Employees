package com.example.kodeemployees.presentation.screens.users.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kodeemployees.R
import com.example.kodeemployees.databinding.ViewUserItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun userItemDelegate(onUserClick: (user: User) -> Unit) =
    adapterDelegateViewBinding<User, User, ViewUserItemBinding>(
        { layoutInflater, parent ->
            ViewUserItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {
        binding.vItemUser.setOnClickListener {
            onUserClick(item)
        }

        bind {
            with(binding) {
                vNameUser.text = item.userName
                vTagUser.text = item.userTag
                vProfession.text = item.profession

                val imgUrl = item.avatarUrl
                Glide.with(vAvatar)
                    .load(imgUrl)
                    .error(R.drawable.img_mock_photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(vAvatar)
            }
        }
    }