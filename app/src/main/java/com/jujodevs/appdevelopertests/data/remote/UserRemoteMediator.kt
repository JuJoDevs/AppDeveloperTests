package com.jujodevs.appdevelopertests.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jujodevs.appdevelopertests.data.local.UserDatabase
import com.jujodevs.appdevelopertests.data.local.UserEntity
import com.jujodevs.appdevelopertests.data.remote.mapper.toUserEntity
import java.io.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userDb: UserDatabase,
    private val userApi: UserApi
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        lastItem.page + 1
                    }
                }
            }

            val users = userApi.getUsers(
                page = loadKey,
                results = state.config.pageSize,
            )

            userDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDb.dao.clearAll()
                }
                userDb.dao.upsertAll(users.toUserEntity(loadKey))
            }

            MediatorResult.Success(
                endOfPaginationReached = users.results.isEmpty(),
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
