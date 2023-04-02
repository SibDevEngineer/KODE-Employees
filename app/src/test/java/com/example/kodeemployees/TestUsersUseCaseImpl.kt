package com.example.kodeemployees

import com.example.kodeemployees.data.dto.toUser
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.domain.UsersUseCase
import com.example.kodeemployees.presentation.models.DepartmentType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class TestUsersUseCaseImpl {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testKodeAPI = TestKodeAPI()
    private val testRepository = mock<MainRepository>()

    /** Тест, проверяющий корректность сортировки списка пользователей по алфавиту */
    @Test
    fun sortListUsersByAlphabet_isCorrect() = runTest {
        val requestParams = RequestParams(sortedBy = SortUsersType.ALPHABET)
        val testUseCase = UsersUseCase(testRepository)

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val actualUsersList = testRepository.getUsers(requestParams)
        val sortedUsersList = testUseCase.getUsers(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность сортировки списка пользователей по дате рождения */
    @Test
    fun sortListUsersByBirthdate_isCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val requestParams = RequestParams(sortedBy = SortUsersType.BIRTHDATE)

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val actualUsersList = testRepository.getUsers(requestParams)
        val sortedUsersList = testUseCase.getUsers(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность фильтра пользователей по департаментам */
    @Test
    fun filterListUsersByDepartment_isCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val choseDepartment = DepartmentType.ANDROID
        val requestParams = RequestParams(department = choseDepartment)

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val filteredUsersList = testUseCase.getUsers(requestParams)

        val actualCountUsers = filteredUsersList.count { it.department != choseDepartment.name.lowercase() }
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по имени */
    @Test
    fun findUserByName_isCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "Homer Simpson")

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val usersList = testUseCase.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по тэгу */
    @Test
    fun findUserByTag_isCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "hs")

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val usersList = testUseCase.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по номеру телефона */
    @Test
    fun findUserByPhone_isCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "533-368-5052")

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val usersList = testUseCase.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность логики поиска в случае, когда пользователь не найден */
    @Test
    fun findUserByName_isNotCorrect() = runTest {
        val testUseCase = UsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "Bender")

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testKodeAPI.getMockUsers().usersList.map { it.toUser() }
        )

        val usersList = testUseCase.getUsers(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }
}