package com.jujodevs.appdevelopertests.data.datasources

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.domain.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun pagingUser(): Flow<PagingData<User>>

    suspend fun getUser(id: Int): User?
}
