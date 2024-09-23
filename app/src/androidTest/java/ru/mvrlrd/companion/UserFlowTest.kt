package ru.mvrlrd.companion

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import ru.mvrlrd.chat_settings.SwitchItem
import ru.mvrlrd.main.MainActivity

class UserFlowTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addNewChat(){
        val context = ApplicationProvider.getApplicationContext<Application>()
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.feature_home.R.string.test_tag_fab)).performClick()

        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_name), true).assertTextEquals(DEFAULT_NAME)
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_role), true).assertTextEquals(DEFAULT_ROLE)
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_max_tokens), true).assertTextEquals(DEFAULT_MAX_TOKENS)
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_switch_prompt)).assertIsOn()
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_switch_stream)).assertIsOff()
//        composeTestRule.onNodeWithTag("SLIDER_TEMPERATURE").assert

        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_switch_stream)).performClick()
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_switch_stream)).assertIsOn()
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_switch_prompt)).performClick()
        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_switch_prompt)).assertIsOff()
        composeTestRule.onNodeWithText(DONE_BUTTON_TEXT).performClick()
        onView(withText(TOAST_TEXT))
            .inRoot(withDecorView(not(`is`(composeTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))

        composeTestRule.onNodeWithTag(context.getString(ru.mvrlrd.chat_settings.R.string.test_tag_et_name)).performTextInput(NEW_CHAT_NAME)
        composeTestRule.onNodeWithText(DONE_BUTTON_TEXT).performClick()
        composeTestRule.onNodeWithText(NEW_CHAT_NAME)
            .assertIsDisplayed()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog(TAG)
    }
    companion object{
        private const val DEFAULT_NAME = ""
        private const val DEFAULT_ROLE = "умный ассистент"
        private const val DEFAULT_MAX_TOKENS = "1000"
        private const val NEW_CHAT_NAME = "test chat"
        private const val DONE_BUTTON_TEXT = "DONE"
        private const val TOAST_TEXT = "Введите имя"

        private const val TAG = "UserFlowTest"


    }
}