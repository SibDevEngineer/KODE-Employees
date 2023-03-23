package com.example.kodeemployees

import com.example.kodeemployees.data.dto.toUser
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.data.repository.MainRepositoryImpl
import com.example.kodeemployees.presentation.models.DepartmentType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.*

class TestMainRepositoryImpl {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testKodeAPI = TestKodeAPI()

    /** Тест, проверяющий корректность сортировки списка пользователей по алфавиту */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sortListUsersByAlphabet_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(sortedBy = SortUsersType.ALPHABET)

        val actualUsersList = testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        val sortedUsersList = testRepository.getUsers(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность сортировки списка пользователей по дате рождения */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sortListUsersByBirthdate_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(sortedBy = SortUsersType.BIRTHDATE)

        val actualUsersList = testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        val sortedUsersList = testRepository.getUsers(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность фильтра пользователей по департаментам */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun filterListUsersByDepartment_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val choseDepartment = DepartmentType.ANDROID
        val requestParams = RequestParams(department = choseDepartment)
        val filteredUsersList = testRepository.getUsers(requestParams)

        val actualCountUsers = filteredUsersList.count { it.department != choseDepartment.name.lowercase() }
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по имени */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findUserByName_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(searchText = "Homer Simpson")
        val usersList = testRepository.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по тэгу */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findUserByTag_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(searchText = "hs")
        val usersList = testRepository.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по номеру телефона */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findUserByPhone_isCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(searchText = "533-368-5052")
        val usersList = testRepository.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность логики поиска в случае, когда пользователь не найден */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findUserByName_isNotCorrect() = runTest {
        val testRepository = MainRepositoryImpl(testKodeAPI)
        val requestParams = RequestParams(searchText = "Bender")
        val usersList = testRepository.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }
}