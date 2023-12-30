package com.jujodevs.appdevelopertests

import com.jujodevs.appdevelopertests.data.repository.UserRepository
import com.jujodevs.appdevelopertests.testshared.data.datasource.UserRemoteDataSourceFake
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
class ExampleInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var dataSourceFake: UserRemoteDataSourceFake

    @Before
    fun setup() {
        hiltRule.inject()
    }
}
