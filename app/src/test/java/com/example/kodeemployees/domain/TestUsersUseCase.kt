package com.example.kodeemployees.domain

import com.example.kodeemployees.MainCoroutineRule
import com.example.kodeemployees.data.TestRemoteAPI
import com.example.kodeemployees.data.mapper.UserDtoMapper
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.models.Department
import com.example.kodeemployees.domain.repository.UsersRepository
import com.example.kodeemployees.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class TestUsersUseCase {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testRemoteAPI = TestRemoteAPI()
    private val testRepository = mock<UsersRepository>()

    /** Тест, проверяющий корректность сортировки списка пользователей по алфавиту */
    @Test
    fun sortListUsersByAlphabet_isCorrect() = runTest {
        val requestParams = RequestParams(sortedBy = SortUsersType.ALPHABET)
        val testUseCase = GetUsersUseCase(testRepository)
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val actualUsersList = testRepository.getUsers(requestParams)
        val sortedUsersList = testUseCase.invoke(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность сортировки списка пользователей по дате рождения */
    @Test
    fun sortListUsersByBirthdate_isCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val requestParams = RequestParams(sortedBy = SortUsersType.BIRTHDATE)
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val actualUsersList = testRepository.getUsers(requestParams)
        val sortedUsersList = testUseCase.invoke(requestParams)

        assertNotEquals(actualUsersList, sortedUsersList)
    }

    /** Тест, проверяющий корректность фильтра пользователей по департаментам */
    @Test
    fun filterListUsersByDepartment_isCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val choseDepartment = Department.ANDROID
        val requestParams = RequestParams(department = choseDepartment)
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val filteredUsersList = testUseCase.invoke(requestParams)

        val actualCountUsers = filteredUsersList.count { it.department != choseDepartment.name.lowercase() }
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по имени */
    @Test
    fun findUserByName_isCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "Homer Simpson")
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val usersList = testUseCase.invoke(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по тэгу */
    @Test
    fun findUserByTag_isCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "hs")
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val usersList = testUseCase.invoke(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность поиска пользователя по номеру телефона */
    @Test
    fun findUserByPhone_isCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "533-368-5052")
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val usersList = testUseCase.invoke(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 1

        assertEquals(actualCountUsers, expectedCountUsers)
    }

    /** Тест, проверяющий корректность логики поиска в случае, когда пользователь не найден */
    @Test
    fun findUserByName_isNotCorrect() = runTest {
        val testUseCase = GetUsersUseCase(testRepository)
        val requestParams = RequestParams(searchText = "Bender")
        val userDtoMapper = UserDtoMapper()

        Mockito.`when`(testRepository.getUsers(requestParams)).thenReturn(
            testRemoteAPI.getMockUsers().usersList
                .map { userDtoMapper.mapToUser(it) }
        )

        val usersList = testUseCase.invoke(requestParams)

        val actualCountUsers = usersList.count()
        val expectedCountUsers = 0

        assertEquals(actualCountUsers, expectedCountUsers)
    }
}