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
import com.jujodevs.appdevelopertests.data.network.dto.UsersDto
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.testshared.coVerifyNever
import com.jujodevs.testshared.coVerifyOnce
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

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
    fun `GIVEN user WHEN load refresh THEN get the expected mediator result`() =
        runTest {
            val loadType = LoadType.REFRESH
            val page = 1
            val pageSize = 20
            val state: PagingState<Int, UserEntity> = mockk()
            val config = PagingConfig(pageSize)
            val users = listOf<User>()

            every { state.config } returns config
            coEvery { userRemote.getUsers(page, pageSize) } returns users
            coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

            val result = mediator.load(loadType, state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(page, pageSize)
                userDao.upsertAll(users.toUserEntity(page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load prepend with last item null THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.PREPEND
        val page = 1
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity? = null
        val users = listOf<User>()

        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load prepend with last item with page 1 THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.PREPEND
        val page = 1
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity = mockk()
        val users = listOf<User>()

        every { lastItem.page } returns page
        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load append with last item null THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.APPEND
        val page = 1
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity? = null
        val users = listOf<User>()

        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load append with last item user page 1 THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.APPEND
        val page = 2
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity = mockk()
        val users = listOf<User>()

        every { lastItem.page } returns page - 1
        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with users list is not empty THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.APPEND
        val page = 2
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity = mockk()
        val users = listOf(User())

        every { lastItem.page } returns page - 1
        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe false

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with users list is empty THEN get the expected mediator result`() = runTest {
        val loadType = LoadType.APPEND
        val page = 2
        val pageSize = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config = PagingConfig(pageSize)
        val lastItem: UserEntity = mockk()
        val users = listOf<User>()

        every { lastItem.page } returns page - 1
        every { state.config } returns config
        every { state.lastItemOrNull() } returns lastItem
        coEvery { userRemote.getUsers(page, pageSize) } returns users
        coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

        val result = mediator.load(loadType, state)

        result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
        (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

        coVerifyOnce {
            userRemote.getUsers(page, pageSize)
            userDao.upsertAll(users.toUserEntity(page))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with remote error THEN get error`() =
        runTest {
            val loadType = LoadType.REFRESH
            val page = 1
            val pageSize = 20
            val state: PagingState<Int, UserEntity> = mockk()
            val config = PagingConfig(pageSize)
            val users = listOf<User>()
            val messageError = "Remote error"
            val error = HttpException(Response.error<UsersDto>(500, messageError.toResponseBody()))

            every { state.config } returns config
            coEvery{ userRemote.getUsers(page, pageSize) } throws error
            coJustRun { userDao.upsertAll(users.toUserEntity(page)) }

            val result = mediator.load(loadType, state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Error::class
            (result as RemoteMediator.MediatorResult.Error).throwable shouldBeInstanceOf HttpException::class
            result.throwable shouldBe error

            coVerifyOnce { userRemote.getUsers(page, pageSize) }
            coVerifyNever { userDao.upsertAll(users.toUserEntity(page)) }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with local error THEN get error`() =
        runTest {
            val loadType = LoadType.REFRESH
            val page = 1
            val pageSize = 20
            val state: PagingState<Int, UserEntity> = mockk()
            val config = PagingConfig(pageSize)
            val users = listOf<User>()
            val messageError = "Remote error"
            val error = IOException(messageError)

            every { state.config } returns config
            coEvery { userRemote.getUsers(page, pageSize) } returns users
            coEvery { userDao.upsertAll(users.toUserEntity(page)) } throws error

            val result = mediator.load(loadType, state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Error::class
            (result as RemoteMediator.MediatorResult.Error).throwable shouldBeInstanceOf IOException::class
            result.throwable shouldBe error

            coVerifyOnce {
                userRemote.getUsers(page, pageSize)
                userDao.upsertAll(users.toUserEntity(page))
            }
        }
}
