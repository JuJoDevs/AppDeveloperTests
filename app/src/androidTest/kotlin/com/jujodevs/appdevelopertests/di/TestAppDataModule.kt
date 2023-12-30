package com.jujodevs.appdevelopertests.di

import com.jujodevs.appdevelopertests.data.datasources.UserLocalDataSource
import com.jujodevs.appdevelopertests.data.datasources.UserRemoteDataSource
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import com.jujodevs.appdevelopertests.domain.repository.UserRepositoryContract
import com.jujodevs.appdevelopertests.testshared.data.datasource.UserDataSourceFake
import com.jujodevs.appdevelopertests.testshared.data.datasource.UserRemoteDataSourceFake
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [AppDataModule::class])
@Module
class TestAppDataModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserLocalDataSource
    ): UserRepositoryContract = UserRepository(userDataSource)

    @Provides
    @Singleton
    fun provideTestUserLocalDataSourceFake() = UserDataSourceFake()

    @Provides
    @Singleton
    fun provideTestUserRemoteDataSourceFake() = UserRemoteDataSourceFake()

    @Provides
    @Singleton
    fun provideTestUserLocalDataSource(fake: UserDataSourceFake): UserLocalDataSource = fake

    @Provides
    @Singleton
    fun provideTestUserRemoteDataSource(fake: UserRemoteDataSource): UserRemoteDataSource = fake
}
