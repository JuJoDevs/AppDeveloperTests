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
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class UserRemoteMediatorTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

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
            val testParams = SuccesTestParams(
                loadType = LoadType.REFRESH,
                page = 1,
                users = listOf(),
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load prepend with last item null THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.PREPEND,
                page = 1,
                users = listOf(),
                lastItem = null,
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load prepend with last item with page 1 THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.PREPEND,
                page = 1,
                users = listOf(),
            )

            every { testParams.lastItem?.page } returns testParams.page

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load append with last item null THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.APPEND,
                page = 1,
                users = listOf(),
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load append with last item user page 1 THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.APPEND,
                page = 2,
                users = listOf(),
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with users list is not empty THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.APPEND,
                page = 2,
                users = listOf(User()),
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe false

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with users list is empty THEN get the expected mediator result`() =
        runTest {
            val testParams = SuccesTestParams(
                loadType = LoadType.APPEND,
                page = 2,
                users = listOf(),
            )

            val result = mediator.load(testParams.loadType, testParams.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached shouldBe true

            coVerifyOnce {
                userRemote.getUsers(testParams.page, testParams.pageSize)
                userDao.upsertAll(testParams.users.toUserEntity(testParams.page))
            }
        }

    private inner class SuccesTestParams(
        val loadType: LoadType,
        val page: Int,
        val users: List<User>,
        val lastItem: UserEntity? = mockk(),
    ) {
        val pageSize: Int = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config: PagingConfig = PagingConfig(pageSize)

        init {
            lastItem?.let {
                every { lastItem.page } returns page - 1
            }
            every { state.config } returns config
            every { state.lastItemOrNull() } returns lastItem
            coEvery { userRemote.getUsers(page, pageSize) } returns users
            coJustRun { userDao.upsertAll(users.toUserEntity(page)) }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with remote error THEN get error`() =
        runTest {
            val messageError = "Remote error"

            val params = ErrorTestParams(
                HttpException(Response.error<UsersDto>(500, messageError.toResponseBody()))
            )

            coEvery { userRemote.getUsers(params.page, params.pageSize) } throws params.error
            coJustRun { userDao.upsertAll(params.users.toUserEntity(params.page)) }

            val result = mediator.load(params.loadType, params.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Error::class
            (result as RemoteMediator.MediatorResult.Error).throwable shouldBeInstanceOf HttpException::class
            result.throwable shouldBe params.error

            coVerifyOnce { userRemote.getUsers(params.page, params.pageSize) }
            coVerifyNever { userDao.upsertAll(params.users.toUserEntity(params.page)) }
        }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN user WHEN load with local error THEN get error`() =
        runTest {
            val messageError = "Local error"

            val params = ErrorTestParams(IOException(messageError))

            coEvery { userRemote.getUsers(params.page, params.pageSize) } returns params.users
            coEvery { userDao.upsertAll(params.users.toUserEntity(params.page)) } throws params.error

            val result = mediator.load(params.loadType, params.state)

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Error::class
            (result as RemoteMediator.MediatorResult.Error).throwable shouldBeInstanceOf IOException::class
            result.throwable shouldBe params.error

            coVerifyOnce {
                userRemote.getUsers(params.page, params.pageSize)
                userDao.upsertAll(params.users.toUserEntity(params.page))
            }
        }

    internal class ErrorTestParams(
        val error: Exception,
    ) {
        val loadType: LoadType = LoadType.REFRESH
        val page: Int = 1
        val pageSize: Int = 20
        val state: PagingState<Int, UserEntity> = mockk()
        val config: PagingConfig = PagingConfig(pageSize)
        val users: List<User> = listOf()

        init {
            every { state.config } returns config
        }
    }
}
