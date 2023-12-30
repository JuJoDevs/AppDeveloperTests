package com.jujodevs.appdevelopertests.di

import android.app.Application
import androidx.room.Room
import com.jujodevs.appdevelopertests.data.database.UserDatabase
import com.jujodevs.appdevelopertests.data.database.UserDatabaseDataSource
import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import com.jujodevs.appdevelopertests.testshared.data.datasource.UserRemoteDataSourceFake
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppDataModule::class, AppExtrasModule::class]
)
@Module
class TestAppDataModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        UserDatabase::class.java
    )
        .setTransactionExecutor(Dispatchers.Main.asExecutor())
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserLocalDataSource
    ): UserRepositoryContract = UserRepository(userDataSource)

    @Provides
    @Singleton
    fun provideTestUserRemoteDataSourceFake() = UserRemoteDataSourceFake()

    @Provides
    @Singleton
    fun provideTestUserLocalDataSource(
        dataSource: UserDatabaseDataSource
    ): UserLocalDataSource = dataSource

    @Provides
    @Singleton
    fun provideTestUserRemoteDataSource(fake: UserRemoteDataSourceFake): UserRemoteDataSource = fake
}
