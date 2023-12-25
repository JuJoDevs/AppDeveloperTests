package com.jujodevs.appdevelopertests.framework.network

import com.jujodevs.appdevelopertests.framework.network.dto.UsersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET(".")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("results") results: Int = 20,
        @Query("seed") seed: String = "appdevelopertests"
    ): UsersDto

    companion object {
        const val BASE_URL = "https://randomuser.me/api/"
    }
}
