package com.example.myunittestsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.example.myunittestsample.utils.getOrAwaitValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(MainRepository(MainApiService()))
    }

    @Test
    fun emptyEmailTest() {
        val result = mainViewModel.validateUser("","123456").getOrAwaitValue()
        assertEquals(ALL_FIELDS_MANDATORY, result)
    }

    @Test
    fun emptyPasswordTest() {
        val result = mainViewModel.validateUser("123@456.com","").getOrAwaitValue()
        assertEquals(ALL_FIELDS_MANDATORY, result)
    }

    @Test
    fun invalidEmailTest() {
        val result = mainViewModel.validateUser("123@456", "123456").getOrAwaitValue()
        assertEquals(INVALID_EMAIL_FORMAT, result)
    }

    @Test
    fun invalidPasswordTest() {
        val result = mainViewModel.validateUser("123@456.com","1234").getOrAwaitValue()
        assertEquals(INVALID_PASSWORD, result)
    }

    @Test
    fun validCredentialsTest() {
        val result = mainViewModel.validateUser("123@456.com","123456").getOrAwaitValue()
        assertEquals(VALID_CREDENTIALS, result)
    }

    @After
    fun tearDown() {
    }
}