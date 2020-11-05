package com.example.myunittestsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.myunittestsample.utils.tilHasError
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainViewmodelUITest {

    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()

    private lateinit var idlingResource: IdlingResource


    @Before
    fun setup() {
//        val activityScenario: ActivityScenario<*> = ActivityScenario.launch(
//            MainActivity::class.java
//        )
//        activityScenario.onActivity {
//            idlingResource = (it as MainActivity).getIdlingResource()!!
//            // To prove that the test fails, omit this call:
//            IdlingRegistry.getInstance().register(idlingResource);
//        }
        Intents.init()
    }

    @Test
    fun validateUIElements() {
        onView(withId(R.id.etUserId)).check(matches(isDisplayed()))
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRegister)).check(matches(isDisplayed()))
    }

    @Test
    fun emptyFieldsTest() {
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.tilUserID))
            .check(matches(tilHasError(ALL_FIELDS_MANDATORY)))
        onView(withId(R.id.tilPassword))
            .check(matches(tilHasError(ALL_FIELDS_MANDATORY)))
    }

    @Test
    fun emptyUserIdTest() {
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("123456"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.tilUserID))
            .check(matches(tilHasError(ALL_FIELDS_MANDATORY)))
    }

    @Test
    fun emptyPasswordTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("qwerty@123.com"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.tilPassword))
            .check(matches(tilHasError(ALL_FIELDS_MANDATORY)))
    }

    @Test
    fun invalidEmailFormatTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("qwerty@123"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("123456"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.tilUserID))
            .check(matches(tilHasError(INVALID_EMAIL_FORMAT)))
    }

    @Test
    fun invalidPasswordTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("qwerty@123.com"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.tilPassword))
            .check(matches(tilHasError(INVALID_PASSWORD)))
    }

    @Test
    fun userNotFoundTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("qwerty@123.com"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("123456"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withText(USER_NOT_FOUND))
            .inRoot(withDecorView(not(`is`(mainActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))

    }

    @Test
    fun invalidCredentialsTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("123@456.com"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("1234567"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withText(INVALID_CREDENTIALS))
            .inRoot(withDecorView(not(`is`(mainActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun userAuthenticatedTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("123@456.com"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("123456"))
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        Thread.sleep(4000)
        Intents.intended(IntentMatchers.hasComponent(HomeActivity::class.java.name))
        onView(withText("Welcome 123@456.com")).check(matches(isDisplayed()))
    }

    @Test
    fun userRegisteredTest() {
        onView(withId(R.id.etUserId)).perform(ViewActions.typeText("qwerty@123.com"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("qwerty"))
        onView(withId(R.id.btnRegister)).perform(ViewActions.click())
        onView(withText(REGISTRATION_SUCCESSFUL)).inRoot(withDecorView(not(`is`(mainActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
//        IdlingRegistry.getInstance().unregister(idlingResource)
        Intents.release()
    }
}