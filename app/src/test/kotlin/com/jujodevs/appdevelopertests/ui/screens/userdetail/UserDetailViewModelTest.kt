package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.usecases.GetUserUseCase
import com.jujodevs.testshared.coVerifyNever
import com.jujodevs.testshared.coVerifyOnce
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import com.jujodevs.testshared.verifyOnce
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class UserDetailViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getUserUseCase: GetUserUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: UserDetailViewModel

    @Test
    fun `GIVEN id is not null WHEN getUser THEN fetch this user if it's not null`() = runTest {
        val id = 1
        val user = User(id = id, name = "test")
        val slot = slot<Int>()
        every { savedStateHandle.get<Int>(any()) } returns id
        coEvery { getUserUseCase(capture(slot)) } returns user
        viewModel = UserDetailViewModel(getUserUseCase, savedStateHandle)

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = false)
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = true)
            awaitItem() shouldBeEqualTo UserDetailUiState(loading = false, user = user)
        }

        slot.captured shouldBeEqualTo id
        verifyOnce { savedStateHandle.get<Int>(any()) }
        coVerifyOnce { getUserUseCase(any()) }
    }

    @Test
    fun `GIVEN id is not null WHEN getUser THEN fetch null if it's null`() = runTest {
        val id = 1
        val user = null
        val slot = slot<Int>()
        every { savedStateHandle.get<Int>(any()) } returns id
        coEvery { getUserUseCase(capture(slot)) } returns user
        viewModel = UserDetailViewModel(getUserUseCase, savedStateHandle)

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = false)
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = true)
            awaitItem() shouldBeEqualTo UserDetailUiState(loading = false, user = null)
        }

        slot.captured shouldBeEqualTo id
        verifyOnce { savedStateHandle.get<Int>(any()) }
        coVerifyOnce { getUserUseCase(any()) }
    }

    @Test
    fun `GIVEN id is null WHEN getUser THEN fetch null`() = runTest {
        val id = null
        every { savedStateHandle.get<Int>(any()) } returns id
        viewModel = UserDetailViewModel(getUserUseCase, savedStateHandle)

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = false)
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = true)
            awaitItem() shouldBeEqualTo  UserDetailUiState(loading = false)
        }

        verifyOnce { savedStateHandle.get<Int>(any()) }
        coVerifyNever { getUserUseCase(any()) }
    }
}
