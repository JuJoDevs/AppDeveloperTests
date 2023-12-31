package com.jujodevs.appdevelopertests.ui

import androidx.test.espresso.IdlingRegistry
import com.jujodevs.appdevelopertests.data.remote.MockWebServerRule
import com.jujodevs.appdevelopertests.idlingresources.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@Suppress("LeakingThis")
@HiltAndroidTest
abstract class InstrumentedTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun parentSetUp() {
        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("Okhttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }
}
