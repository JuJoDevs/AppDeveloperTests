package com.jujodevs.appdevelopertests.domain.repository

import androidx.paging.PagingData // without Android dependencies
import com.jujodevs.appdevelopertests.domain.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryContract {
    fun pagingUser(): Flow<PagingData<User>>

    suspend fun getUser(id: Int): User?
}
