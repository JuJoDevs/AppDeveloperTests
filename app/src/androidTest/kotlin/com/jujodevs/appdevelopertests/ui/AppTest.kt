package com.jujodevs.appdevelopertests.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.jujodevs.appdevelopertests.MainActivity
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailBackTag
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailScreenTag
import com.jujodevs.appdevelopertests.ui.screens.users.UserRowTag
import com.jujodevs.appdevelopertests.ui.screens.users.UsersBackTag
import com.jujodevs.appdevelopertests.ui.screens.users.UsersLazyColumnTag
import com.jujodevs.appdevelopertests.ui.screens.users.UsersMenuTag
import com.jujodevs.appdevelopertests.ui.screens.users.UsersScreenTag
import com.jujodevs.appdevelopertests.ui.screens.users.UsersSearchTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

private const val Row1UserName = "Norbert Roussel"
private const val Row49UserName = "Alicia Brown"

@HiltAndroidTest
class AppTest : InstrumentedTest() {

    @get:Rule(order = 2)
    val activityRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun t_GIVE_users_WHEN_app_starts_THEN_Norbert_Rousel_is_displayed(): Unit = with(activityRule) {
        onNodeWithTag(UserRowTag + "1")
            .assertTextContains(Row1UserName)
            .assertIsDisplayed()
    }

    /**
     * Paging 3 doesn't work correctly in androidTest
     * If it fail, reset IDE and try again
     */
    @Test
    fun t_GIVE_users_WHEN_scroll_up_THEN_Alicia_Brown_is_displayed(): Unit = with(activityRule) {
        onNodeWithTag(UsersLazyColumnTag)
            .performScrollToIndex(19)
            .performTouchInput { swipeUp(100f, -100f) }

        Thread.sleep(1000)

        onNodeWithTag(UsersLazyColumnTag)
            .performScrollToIndex(39)
            .performTouchInput { swipeUp(100f, -100f) }

        Thread.sleep(1000)

        onNodeWithTag(UsersLazyColumnTag)
            .performScrollToIndex(49)

        onNodeWithTag(UserRowTag + "1")
            .assertDoesNotExist()

        onNodeWithTag(UserRowTag + "49")
            .assertIsDisplayed()
            .assertTextContains(Row49UserName)
    }

    @Test
    fun t_GIVE_users_WHEN_click_THEN_Norbert_Rousel_is_displayed(): Unit = with(activityRule) {
        onNodeWithTag(UserRowTag + "1")
            .performClick()

        onNodeWithTag(UserDetailScreenTag)
            .assertExists()

        onNodeWithText(Row1UserName)
            .assertExists()
    }

    @Test
    fun t_GIVE_userDetail_WHEN_click_back_THEN_users_screen_is_displayed(): Unit =
        with(activityRule) {
            onNodeWithTag(UserRowTag + "1")
                .performClick()

            onNodeWithTag(UserDetailBackTag)
                .performClick()

            onNodeWithTag(UsersScreenTag)
                .assertIsDisplayed()
        }

    @Test
    fun t_GIVE_users_WHEN_click_back_THEN_app_finishes(): Unit = with(activityRule) {
        onNodeWithTag(UsersBackTag)
            .performClick()

        activity.isFinishing shouldBe true
    }

    @Test
    fun t_GIVE_users_WHEN_click_menu_THEN_search_text_field_is_displayed(): Unit =
        with(activityRule) {
            onNodeWithTag(UsersMenuTag)
                .performClick()

            onNodeWithTag(UsersSearchTag)
                .assertIsDisplayed()
        }

    @Test
    fun t_GIVE_search_text_field_is_displayed_WHEN_dismiss_THEN_search_text_field_is_not_displayed(): Unit =
        with(activityRule) {
            onNodeWithTag(UsersMenuTag)
                .performClick()

            onNodeWithTag(UsersScreenTag)
                .performClick()

            onNodeWithTag(UsersSearchTag)
                .assertDoesNotExist()
        }

    @Test
    fun t_GIVE_search_text_field_is_displayed_WHEN_text_is_entered_THEN_row_user_is_displayed(): Unit =
        with(activityRule) {
            onNodeWithTag(UsersMenuTag)
                .performClick()

            onNodeWithTag(UsersSearchTag)
                .performTextReplacement(Row49UserName)

            onNodeWithTag(UserRowTag + "49")
                .assertIsDisplayed()
        }
}
