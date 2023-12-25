package com.jujodevs.appdevelopertests.data.repository

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.domain.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) {
    fun pagingUser(): Flow<PagingData<User>> =
        userLocalDataSource.pagingUser()

    suspend fun getUser(id: Int): User? =
        userLocalDataSource.getUser(id)
}
