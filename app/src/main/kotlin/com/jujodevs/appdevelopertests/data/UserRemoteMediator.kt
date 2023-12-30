package com.jujodevs.appdevelopertests.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.jujodevs.appdevelopertests.data.database.UserDao
import com.jujodevs.appdevelopertests.data.database.UserEntity
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.data.mapper.toUserEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val userDao: UserDao,
    private val userRemote: UserRemoteDataSource
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.page ?: 1
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        lastItem.page + 1
                    }
                }
            }

            val users = userRemote.getUsers(
                page = loadKey,
                results = state.config.pageSize,
            )

            userDao.upsertAll(users.toUserEntity(loadKey))

            MediatorResult.Success(
                endOfPaginationReached = users.isEmpty(),
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
    fun pagingSource() = userDao.pagingSource()
}
