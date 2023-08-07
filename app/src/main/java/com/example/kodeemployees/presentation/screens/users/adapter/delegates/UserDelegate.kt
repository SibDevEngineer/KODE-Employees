package com.example.kodeemployees.presentation.screens.users.adapter.delegates

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kodeemployees.R
import com.example.kodeemployees.databinding.ViewUserItemBinding
import com.example.kodeemployees.core.extensions.gone
import com.example.kodeemployees.core.extensions.show
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun userItemDelegate(onUserClick: (userId: String) -> Unit) =
    adapterDelegateViewBinding<UserItemUi.User, UserItemUi, ViewUserItemBinding>(
        { layoutInflater, parent ->
            ViewUserItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {
        binding.vItemUser.setOnClickListener {
            onUserClick(item.id)
        }

        bind {
            with(binding) {
                vNameUser.text = item.userName
                vTagUser.text = item.userTag
                vProfession.text = item.profession

                if (item.isShowBirthdate) {
                    vBirthDateItem.show()
                    vBirthDateItem.text = item.birthdateUI
                } else vBirthDateItem.gone()

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