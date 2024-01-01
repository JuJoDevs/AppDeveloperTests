package com.jujodevs.appdevelopertests.data.database

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.di.IO
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.data.mapper.entityToUser
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserDatabaseDataSource @Inject constructor(
    private val userDao: UserDao,
    private val pager: Pager<Int, UserEntity>,
    @IO private val dispatcher: CoroutineDispatcher
) : UserLocalDataSource {
    override fun pagingUser(): Flow<PagingData<User>> =
        pager.flow.map { pagingData ->
            pagingData.map { it.entityToUser() }
        }

    override suspend fun getUser(id: Int): User? =
        withContext(dispatcher) { userDao.getUser(id)?.entityToUser() }
}
