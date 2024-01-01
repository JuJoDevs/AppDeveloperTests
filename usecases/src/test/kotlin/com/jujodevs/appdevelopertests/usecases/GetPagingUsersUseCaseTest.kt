package com.jujodevs.appdevelopertests.usecases

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import com.jujodevs.testshared.coVerifyOnce
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GetPagingUsersUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var userRepository: UserRepositoryContract

    private lateinit var getPagingUsersUseCase: GetPagingUsersUseCase

    private val expectedUsers = listOf(User(id = 1), User(id = 2))
    private val expectedPaging = PagingData.from(expectedUsers)

    @Test
    fun `WHEN GetPagingUsersUseCase is called THEN it should return a paging of users`() {
        every { userRepository.pagingUser() } returns flowOf(expectedPaging)
        getPagingUsersUseCase = GetPagingUsersUseCase(userRepository)

        val result = runBlocking { getPagingUsersUseCase().first() }

        coVerifyOnce { userRepository.pagingUser() }
        assertEquals(expectedPaging, result)
    }
}
