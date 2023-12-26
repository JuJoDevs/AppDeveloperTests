package com.jujodevs.appdevelopertests.data.repository

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.domain.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var localDataSource: UserLocalDataSource

    private lateinit var userRepository: UserRepository

    private val expectedUsers = listOf(User(id = 1), User(id = 2))
    private val expectedPaging = PagingData.from(expectedUsers)

    @Before
    fun setUp() {
        coEvery { localDataSource.getUser(1) } returns expectedUsers[0]
        coEvery { localDataSource.getUser(2) } returns expectedUsers[1]
        every { localDataSource.pagingUser() } returns flowOf(expectedPaging)

        userRepository = UserRepository(localDataSource)
    }

    @Test
    fun `WHEN pagingUser THEN call localDataSource pagingUser once`() {
        val result = runBlocking { userRepository.pagingUser().first() }

        coVerify(exactly = 1) { localDataSource.pagingUser() }
        assertEquals(expectedPaging, result)
    }

    @Test
    fun `WHEN getUser THEN call localDataSource getUser once`() {
        val result1 = runBlocking { userRepository.getUser(1) }
        val result2 = runBlocking { userRepository.getUser(2) }

        coVerify(exactly = 2) { localDataSource.getUser(any()) }

        assertEquals(expectedUsers[0], result1)
        assertEquals(expectedUsers[1], result2)
    }
}
