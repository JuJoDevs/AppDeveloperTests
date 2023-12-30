package com.jujodevs.appdevelopertests.data.network

import com.jujodevs.appdevelopertests.data.mapper.dtoToUser
import com.jujodevs.appdevelopertests.data.network.dto.InfoDto
import com.jujodevs.appdevelopertests.data.network.dto.UserDto
import com.jujodevs.appdevelopertests.data.network.dto.UsersDto
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserServerDataSourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userApi: UserApi = mockk()

    private lateinit var serverDataSource: UserServerDataSource


    @Before
    fun setup() {
        serverDataSource = UserServerDataSource(userApi, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `GIVEN page WHEN getUsers THEN returns expected users`() = runTest {
        val usersRemote = listOf(DefaultUserDto.userDto)
        val results = 20
        val page = 1
        val info = InfoDto(results = results, page = page)
        val usersDto = UsersDto(results = usersRemote, info = info)
        val users = usersDto.dtoToUser()

        coEvery { userApi.getUsers(page, results) } returns usersDto

        serverDataSource.getUsers(page, results) shouldBeEqualTo users
    }

    @Test
    fun `GIVEN empty page WHEN getUsers THEN returns empty list users`() = runTest {
        val usersRemote = emptyList<UserDto>()
        val results = 20
        val page = 4
        val info = InfoDto(results = results, page = page)
        val usersDto = UsersDto(results = usersRemote, info = info)
        val users = usersDto.dtoToUser()

        coEvery { userApi.getUsers(page, results) } returns usersDto

        serverDataSource.getUsers(page, results) shouldBeEqualTo users
    }
}
