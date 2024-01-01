package com.jujodevs.appdevelopertests.di

import android.app.Application
import androidx.room.Room
import com.jujodevs.appdevelopertests.data.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

private const val Url = "http://localhost:8080/"

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppExtrasModule::class]
)
@Module
class TestAppModule {

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
    @ApiUrl
    fun provideApiUrl(): String = Url
}
