package com.example.kodeemployees.screens

import com.example.kodeemployees.R
import com.example.kodeemployees.presentation.screens.users.sort_users.SortUsersFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton


object SortUsersDialogScreen : KScreen<SortUsersDialogScreen>() {
    override val layoutId: Int = R.layout.fragment_sort_users
    override val viewClass: Class<*> = SortUsersFragment::class.java

    val sortByAlphabetBtn = KButton{withId(R.id.vSortByAlphabetBtn)}
    val sortByBirthdateBtn = KButton{withId(R.id.vSortByBirthdateBtn)}
}