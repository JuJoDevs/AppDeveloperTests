package com.jujodevs.appdevelopertests.data.network

import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.di.IO
import com.jujodevs.appdevelopertests.data.mapper.dtoToUser
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserServerDataSource @Inject constructor(
    private val userApi: UserApi,
    @IO private val dispatcher: CoroutineDispatcher
) : UserRemoteDataSource {
    override suspend fun getUsers(page: Int, results: Int) = withContext(dispatcher) {
        userApi.getUsers(
            page,
            results,
        ).dtoToUser()
    }
}
