@file:SuppressLint("VisibleForTests")

package com.jujodevs.appdevelopertests.testshared.data.datasource

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jujodevs.appdevelopertests.data.UserRemoteMediator
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.data.mapper.entityToUser
import com.jujodevs.appdevelopertests.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataSourceFake(
    private val userDao: UserDaoFake = UserDaoFake(),
    private val userRemoteMediator: UserRemoteMediator =
        UserRemoteMediator(userDao, UserRemoteDataSourceFake())
) : UserLocalDataSource {

    @OptIn(ExperimentalPagingApi::class)
    private val users = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = userRemoteMediator,
        pagingSourceFactory = { userRemoteMediator.pagingSource() },
    )

    override fun pagingUser(): Flow<PagingData<User>> =
        users.flow.map { pagingData -> pagingData.map { it.entityToUser() } }

    override suspend fun getUser(id: Int): User? =
        userDao.getUser(id)?.entityToUser()
}
