package com.jujodevs.appdevelopertests.data.datasources

import com.jujodevs.appdevelopertests.domain.User

interface UserRemoteDataSource {
    suspend fun getUsers(page: Int, results: Int): List<User>
}
