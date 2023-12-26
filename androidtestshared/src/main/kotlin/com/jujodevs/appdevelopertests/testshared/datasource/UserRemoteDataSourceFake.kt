package com.jujodevs.appdevelopertests.testshared.datasource

import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.testshared.FakeUsers

class UserRemoteDataSourceFake(
    private val users: List<User> = FakeUsers.users
) : UserRemoteDataSource {
    override suspend fun getUsers(page: Int, results: Int): List<User> =
        users.filter { it.id < page * results && it.id >= (page - 1) * results }
}
