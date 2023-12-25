@file:Suppress("unused")

package com.jujodevs.appdevelopertests.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.framework.UserRemoteMediator
import com.jujodevs.appdevelopertests.framework.database.UserDao
import com.jujodevs.appdevelopertests.framework.database.UserDatabase
import com.jujodevs.appdevelopertests.framework.database.UserDatabaseDataSource
import com.jujodevs.appdevelopertests.framework.database.UserEntity
import com.jujodevs.appdevelopertests.framework.network.UserApi
import com.jujodevs.appdevelopertests.framework.network.UserServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ResponseTimeOutSeconds: Long = 100

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(UserApi.BASE_URL)
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
abstract class AppDataModule {
    @Binds
    abstract fun bindsLocalDataSource(impl: UserDatabaseDataSource): UserLocalDataSource

    @Binds
    abstract fun bindsRemoteDataSource(impl: UserServerDataSource): UserRemoteDataSource
}
