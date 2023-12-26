package com.jujodevs.appdevelopertests.usecases

import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GetUserUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var userRepository: UserRepositoryContract

    private lateinit var getUserUseCase: GetUserUseCase

    private val expectedUser = User(id = 1)

    @Test
    fun `WHEN getUser is called THEN return a User`() {
        coEvery { userRepository.getUser(1) } returns expectedUser
        getUserUseCase = GetUserUseCase(userRepository)

        val result = runBlocking { getUserUseCase(1) }

        coVerify(exactly = 1) { userRepository.getUser(1) }
        assertEquals(expectedUser, result)
    }
}
