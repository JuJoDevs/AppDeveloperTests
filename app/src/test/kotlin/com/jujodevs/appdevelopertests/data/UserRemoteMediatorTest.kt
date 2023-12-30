package com.jujodevs.appdevelopertests.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.jujodevs.appdevelopertests.data.database.UserDao
import com.jujodevs.appdevelopertests.data.database.UserEntity
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.data.mapper.toUserEntity
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.testshared.coVerifyOnce
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test

class UserRemoteMediatorTest {
    private val userDao: UserDao = mockk()
    private val userRemote: UserRemoteDataSource = mockk()

    private lateinit var mediator: UserRemoteMediator

    @Before
    fun setUp() {
        mediator = UserRemoteMediator(userDao, userRemote)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.REFRESH
        val page = 1
        val pageSize = 20
        val config = PagingConfig(
            pageSize = pageSize,
        )
        val pagingState =
            PagingState<Int, UserEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = config,
                leadingPlaceholderCount = pageSize,
            )

        val users = listOf<User>()

        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, pagingState)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }
}
