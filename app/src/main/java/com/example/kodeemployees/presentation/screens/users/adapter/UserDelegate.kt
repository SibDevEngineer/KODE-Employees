package com.example.kodeemployees.presentation.screens.users.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kodeemployees.R
import com.example.kodeemployees.databinding.ViewUserItemBinding
import com.example.kodeemployees.presentation.models.User
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun userItemDelegate(onUserClick: (user: User) -> Unit) =
    adapterDelegateViewBinding<UserItemUI.UserUI, UserItemUI, ViewUserItemBinding>(
        { layoutInflater, parent ->
            ViewUserItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {
        binding.vItemUser.setOnClickListener {
            onUserClick(item.user)
        }

        bind {
            with(binding) {
                vNameUser.text = item.user.userName
                vTagUser.text = item.user.userTag
                vProfession.text = item.user.profession
                if (item.isShowBirthdate) vBirthDate.text = item.birthdateUI

                val imgUrl = item.user.avatarUrl
                Glide.with(vAvatar)
                    .load(imgUrl)
                    .error(R.drawable.img_mock_photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(vAvatar)
            }
        }
    }