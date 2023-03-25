package com.example.kodeemployees.users_screen_tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.kodeemployees.R
import com.example.kodeemployees.presentation.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test


class UsersFragmentTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()


    @Test
    fun checkFirstUserItemWithClickAndReturnBackFromUserDetailScreen() = run {
        step("Проверка первого элемента списка на наличие текста в поле имени пользователя," +
                "далее выполняется переход в экран детальной информации о пользователе") {

            UsersScreen {
                recycler {
                    firstChild<UserItem> {
                        vNameUser.hasAnyText()
                        click()
                    }
                }
            }
        }

        step("Проверка на наличие текста в поле имени пользователя и кнопки навигации тулбара " +
                "для перехода на предыдущий экран") {
            UserDetailScreen {
                userName.hasAnyText()
                toolbarButton.click()
            }
        }
    }

    @Test
    fun changeUiStateSearchFieldElements_isCorrect() = run {
        step("Проверяем видимость кнопки сортировки пользователей") {
            UsersScreen { sortUsersBtn.isVisible()  }
        }

        step("Кликаем на поле поиска пользователя,должна измениться видимость кнопок и цвет иконки поиска") {
            UsersScreen {
                searchEditText.click()
                clearTxtBtn.isVisible()
                cancelBtn.isVisible()
                sortUsersBtn.isGone()
                searchImg { hasDrawableWithTint(R.drawable.ic_search_btn, R.color.black) }
            }
        }

        step("Кликаем на кнопку Отмена, состояние элементов UI должно вернуться к базовому") {
            UsersScreen {
                cancelBtn.click()
                clearTxtBtn.isGone()
                cancelBtn.isGone()
                sortUsersBtn.isVisible()
                searchImg { hasDrawableWithTint(R.drawable.ic_search_btn, R.color.gray2) }
            }
        }
    }
}