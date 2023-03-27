package com.example.kodeemployees.screens

import android.view.View
import com.example.kodeemployees.R
import com.example.kodeemployees.presentation.screens.users.UsersFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.swiperefresh.KSwipeRefreshLayout
import io.github.kakaocup.kakao.tabs.KTabLayout
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object UsersScreen : KScreen<UsersScreen>() {
    override val layoutId: Int = R.layout.fragment_users
    override val viewClass: Class<*> = UsersFragment::class.java

    val recycler = KRecyclerView(
        builder = { withId(R.id.vRecyclerUsers) },
        itemTypeBuilder = {
            itemType(::UserItem)
            itemType(::HeaderItem)
            itemType(::SkeletonItem)
        })

    val swipeRefresh = KSwipeRefreshLayout { withId(R.id.vSwipeRefreshLayout) }

    val sortUsersBtn = KButton { withId(R.id.vSortUsersBtn) }
    val clearTxtBtn = KButton { withId(R.id.vClearTxtBtn) }
    val cancelBtn = KButton { withId(R.id.vCancelTxtBtn) }

    val searchEditText = KEditText { withId(R.id.vSearchEditText) }

    val searchIcon = KImageView { withId(R.id.vSearchImg) }

    val tabLayout = KTabLayout{withId(R.id.vTabLayout)}

    val errorLayout = KView{withId(R.id.vErrorLayout)}
    val errorTitle = KTextView{withId(R.id.vErrorTitle)}
    val retryBtn = KButton{withId(R.id.vRefreshTxtBtn)}
}

class UserItem(parent: Matcher<View>) : KRecyclerItem<UserItem>(parent) {
    val nameUserItem = KTextView(parent) { withId(R.id.vNameUser) }
    val birthdate = KTextView(parent) { withId(R.id.vBirthDateItem) }
}

class HeaderItem(parent: Matcher<View>) : KRecyclerItem<HeaderItem>(parent)

class SkeletonItem(parent: Matcher<View>) : KRecyclerItem<SkeletonItem>(parent)
