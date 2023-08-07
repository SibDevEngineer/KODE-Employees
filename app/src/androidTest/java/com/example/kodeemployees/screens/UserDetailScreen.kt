package com.example.kodeemployees.screens

import com.example.kodeemployees.R
import com.example.kodeemployees.presentation.screens.user_detail.UserDetailFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView


object UserDetailScreen : KScreen<UserDetailScreen>() {
    override val layoutId: Int = R.layout.fragment_user_detail
    override val viewClass: Class<*> = UserDetailFragment::class.java

    val userName = KTextView { withId(R.id.vNameUserDet) }
    val toolbarButton = KImageView { withDrawable(R.drawable.ic_caret_left) }

}