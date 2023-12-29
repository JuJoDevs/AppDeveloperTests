package com.jujodevs.appdevelopertests.testshared.data.datasource

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.testing.asPagingSourceFactory
import com.jujodevs.appdevelopertests.data.database.UserDao
import com.jujodevs.appdevelopertests.data.database.UserEntity

class UserDaoFake : UserDao {

    private val inMemoryUsers = mutableListOf<UserEntity>()

    override suspend fun upsertAll(users: List<UserEntity>) {
        inMemoryUsers.addAll(users)
    }

    @SuppressLint("VisibleForTests")
    override fun pagingSource(): PagingSource<Int, UserEntity> =
        inMemoryUsers.asPagingSourceFactory()()

    override suspend fun getUser(id: Int): UserEntity? =
        inMemoryUsers.firstOrNull { it.id == id }
}
