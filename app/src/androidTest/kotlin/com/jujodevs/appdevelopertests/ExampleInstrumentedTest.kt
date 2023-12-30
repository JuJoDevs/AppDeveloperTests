package com.jujodevs.appdevelopertests

import androidx.paging.testing.asSnapshot
import com.jujodevs.appdevelopertests.data.remote.MockWebServerRule
import com.jujodevs.appdevelopertests.data.repository.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ExampleInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testHiltRoomMockServerWorks() = runTest {
        val pagingUsers = userRepository.pagingUser()
        val result = pagingUsers.asSnapshot {
            refresh()
        }

        result[0].id shouldBeEqualTo 1
        result[0].name shouldBeEqualTo "Norbert Roussel"
    }
}
