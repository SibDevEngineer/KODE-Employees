package com.example.kodeemployees.ui_tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.kodeemployees.R
import com.example.kodeemployees.presentation.MainActivity
import com.example.kodeemployees.screens.*
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Rule
import org.junit.Test


class UsersFragmentTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()


    @Test
    fun checkFirstUserItemWithClickAndReturnBackFromUserDetailScreen() = run {
        step("Проверка наличия скелетонов в списке") {
            UsersScreen.recycler {
                firstChild<SkeletonItem> { isDisplayed() }
            }
        }

        step(
            "Проверка первого элемента списка на наличие текста в поле имени пользователя," +
                    "далее выполняется переход в экран детальной информации о пользователе"
        ) {
            UsersScreen {
                recycler {
                    firstChild<UserItem> {
                        nameUserItem.hasAnyText()
                        click()
                    }
                }
            }
        }

        step(
            "Проверка на наличие текста в поле имени пользователя и кнопки навигации тулбара " +
                    "для перехода на предыдущий экран"
        ) {
            UserDetailScreen {
                userName.hasAnyText()
                toolbarButton.click()
            }
        }
    }

    @Test
    fun changeUiStateSearchFieldElements_isCorrect() = run {
        step("Проверяем видимость кнопки сортировки пользователей") {
            UsersScreen { sortUsersBtn.isVisible() }
        }

        step("Кликаем на поле поиска пользователя,должна измениться видимость кнопок и цвет иконки поиска") {
            UsersScreen {
                searchEditText.click()
                clearTxtBtn.isVisible()
                cancelBtn.isVisible()
                sortUsersBtn.isGone()
                searchIcon { hasDrawableWithTint(R.drawable.ic_search_btn, R.color.black) }
            }
        }

        step("Кликаем на кнопку Отмена, состояние элементов UI должно вернуться к базовому") {
            UsersScreen {
                cancelBtn.click()
                clearTxtBtn.isGone()
                cancelBtn.isGone()
                sortUsersBtn.isVisible()
                searchIcon { hasDrawableWithTint(R.drawable.ic_search_btn, R.color.gray2) }
            }
        }
    }

    @Test
    fun checkSelectTabLayoutAndSwipeRefreshLayout() = run {
        step("Проверка переключения табов") {
            UsersScreen {
                tabLayout {
                    selectTab(0)
                    selectTab(7)
                    selectTab(1)
                    isTabSelected(1)
                    hasDescendant { withText(R.string.department_title_android) }
                }
            }
            step("Проверка SwipeRefresh и состояние выбранной табы") {
                UsersScreen {
                    swipeRefresh {
                        setRefreshing(true)
                        isRefreshing()
                    }
                    tabLayout {
                        isTabSelected(1)
                        hasDescendant { withText(R.string.department_title_android) }
                    }
                }
            }
        }
    }

    @Test
    fun searchUser_isFound() = run {
        UsersScreen {
            searchEditText.replaceText("a")
            recycler {
                firstChild<UserItem> {
                    nameUserItem.containsText("a")
                }
            }
            errorLayout.isGone()
        }
    }

    @Test
    fun searchUser_isNotFound() = run {
        UsersScreen {
            searchEditText.replaceText(getResourceString(R.string.test_users_fragment_title_user_not_found))
            recycler.isNotDisplayed()
            errorLayout.isVisible()
            errorTitle.hasText(R.string.users_screen_state_user_not_found_title)
        }
    }

    @Test
    fun sortUsers_isCorrect() = run {
        step("Выбор сортировки по дате рождения") {
            UsersScreen { sortUsersBtn.click() }
            SortUsersDialogScreen { sortByBirthdateBtn.click() }
        }
        step("Проверка отображения даты рождения у пользователя и наличия хэдера в списке") {
            UsersScreen {
                recycler {
                    isCompletelyDisplayed()
                    firstChild<UserItem> { birthdate.isVisible() }

                    childWith<HeaderItem> {
                        withDescendant { withText(R.string.test_users_fragment_header_title_next_year) }
                    } perform {
                        isDisplayed()
                    }
                }
            }
        }
        step("Проверка отображения даты рождения у пользователя после изменения типа сортировки") {
            UsersScreen {
                sortUsersBtn.click()
                SortUsersDialogScreen { sortByAlphabetBtn.click() }

                recycler {
                    isDisplayed()
                    firstChild<UserItem> { birthdate.isGone() }
                }
            }
        }
    }

    @Test
    fun showStateError_isCorrect() = run {
        step("Проверяем стэйт при выключенном интернет-соединении") {
            UsersScreen {
                device.network.disable()

                swipeRefresh.swipeDown()

                errorLayout.isDisplayed()
                errorTitle.hasText(R.string.users_screen_state_error_title)
            }
        }
        step("Проверяем изменение стэйта при включенном интернет-соединении") {
            UsersScreen {
                device.network.enable()

                retryBtn.click()

                errorLayout.isGone()
                recycler.isDisplayed()
            }
        }
    }
}