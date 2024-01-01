@file:Suppress("unused")

package com.jujodevs.appdevelopertests.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.jujodevs.appdevelopertests.data.UserRemoteMediator
import com.jujodevs.appdevelopertests.data.database.UserDao
import com.jujodevs.appdevelopertests.data.database.UserDatabase
import com.jujodevs.appdevelopertests.data.database.UserDatabaseDataSource
import com.jujodevs.appdevelopertests.data.database.UserEntity
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.data.network.UserApi
import com.jujodevs.appdevelopertests.data.network.UserServerDataSource
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val ResponseTimeOutSeconds: Long = 100
private const val Url: String = "https://randomuser.me/api/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.dao

    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(ResponseTimeOutSeconds, TimeUnit.SECONDS)
            .connectTimeout(ResponseTimeOutSeconds, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @ApiUrl apiUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUserPager(userRemoteMediator: UserRemoteMediator): Pager<Int, UserEntity> =
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = userRemoteMediator,
            pagingSourceFactory = { userRemoteMediator.pagingSource() },
        )
}

@Module
@InstallIn(SingletonComponent::class)
object AppExtrasModule {
    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user.db",
        ).build()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = Url
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindsLocalDataSource(impl: UserDatabaseDataSource): UserLocalDataSource

    @Binds
    abstract fun bindsRemoteDataSource(impl: UserServerDataSource): UserRemoteDataSource

    @Binds
    abstract fun bindsUserRepository(impl: UserRepository): UserRepositoryContract
}
