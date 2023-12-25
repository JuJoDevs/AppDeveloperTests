package com.jujodevs.appdevelopertests.data.datasources

import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.domain.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun upsertAll(users: List<User>, page: Int)

    fun pagingUser(): Flow<PagingData<User>>

    suspend fun getUser(id: Int): User?
}
