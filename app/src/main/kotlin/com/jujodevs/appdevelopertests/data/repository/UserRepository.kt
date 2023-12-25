package com.jujodevs.appdevelopertests.data.repository

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
): UserRepositoryContract {
    override fun pagingUser(): Flow<PagingData<User>> =
        userLocalDataSource.pagingUser()

    override suspend fun getUser(id: Int): User? =
        userLocalDataSource.getUser(id)
}
