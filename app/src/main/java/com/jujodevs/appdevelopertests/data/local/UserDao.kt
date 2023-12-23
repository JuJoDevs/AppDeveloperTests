package com.jujodevs.appdevelopertests.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM UserEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?
}
