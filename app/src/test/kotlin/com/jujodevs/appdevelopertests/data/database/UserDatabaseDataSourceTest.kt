package com.jujodevs.appdevelopertests.data.database

import androidx.paging.Pager
import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.data.mapper.entityToUser
import com.jujodevs.testshared.LazyPagingItemsTest
import com.jujodevs.testshared.coVerifyOnce
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDatabaseDataSourceTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userDao: UserDao = mockk()

    private val pager: Pager<Int, UserEntity> = mockk()

    private lateinit var userDatabaseDataSource: UserDatabaseDataSource

    private val usersEntites = listOf(
        DefaultUserEntity.getUserEntity(1),
        DefaultUserEntity.getUserEntity(2),
    )
    private val pagingDataEntity = PagingData.from(usersEntites)

    private val expectedUsers = usersEntites.map { it.entityToUser() }

    @Before
    fun setUp() {
        coEvery { userDao.getUser(1) } returns usersEntites[0]
        coEvery { userDao.getUser(2) } returns usersEntites[1]
        coEvery { userDao.getUser(3) } returns null
        every { pager.flow } returns flowOf(pagingDataEntity)

        userDatabaseDataSource = UserDatabaseDataSource(userDao, pager, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `GIVEN users WHEN pagingUser THEN return expected users`() = runTest {
        val result = userDatabaseDataSource.pagingUser()

        val state = LazyPagingItemsTest(result)
        state.initPagingDiffer()
        val items = state.itemSnapshotList.items

        coVerifyOnce { userDatabaseDataSource.pagingUser() }

        items shouldBeEqualTo expectedUsers
    }

    @Test
    fun `GIVEN id WHEN getUser THEN return expected user`() = runTest {
        val result1 = userDatabaseDataSource.getUser(1)
        val result2 = userDatabaseDataSource.getUser(2)

        coVerify(exactly = 2) { userDao.getUser(any()) }

        result1 shouldBeEqualTo expectedUsers[0]
        result2 shouldBeEqualTo expectedUsers[1]
    }

    @Test
    fun `GIVEN id that does not exist WHEN getUser THEN return null`() = runTest {
        val result = userDatabaseDataSource.getUser(3)

        coVerifyOnce { userDao.getUser(any()) }

        result shouldBeEqualTo null
    }
}
