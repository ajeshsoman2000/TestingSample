package com.example.myunittestsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.myunittestsample.utils.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mainRepository: MainRepository

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun testLoginCalled() {
        mainViewModel.authenticateUser("123@456.com","123456")
        Mockito.verify(mainRepository).login("123@456.com", "123456")
    }

    @Test
    fun testRegisterCalled() {
        mainViewModel.registerUser("123@456.com","123456")
        Mockito.verify(mainRepository).register("123@456.com","123456")
    }

    @Test
    fun testInvalidCredential() {
        Mockito.`when`(mainRepository.login("123@456.com","1234567")).thenReturn(MutableLiveData<String>().apply {
            value = INVALID_CREDENTIALS
        })
        val result = mainViewModel.authenticateUser("123@456.com","1234567").getOrAwaitValue()
        assertEquals(INVALID_CREDENTIALS, result)
    }

    @Test
    fun testUserNotFound() {
        Mockito.`when`(mainRepository.login("qwerty@test.com","123456")).thenReturn(MutableLiveData<String>().apply {
            value = USER_NOT_FOUND
        })
        val result = mainViewModel.authenticateUser("qwerty@test.com","123456").getOrAwaitValue()
        assertEquals(USER_NOT_FOUND, result)
    }

    @Test
    fun testUserAuthenticated() {
        Mockito.`when`(mainRepository.login("123@456.com","123456")).thenReturn(MutableLiveData<String>().apply {
            value = USER_AUTHENTICATED
        })
        val result = mainViewModel.authenticateUser("123@456.com","123456").getOrAwaitValue()
        assertEquals(USER_AUTHENTICATED, result)
    }

    @Test
    fun testUserRegistered() {
        Mockito.`when`(mainRepository.register("qwerty@123.com","qwerty")).thenReturn(MutableLiveData<String>().apply {
            value = REGISTRATION_SUCCESSFUL
        })
        val result = mainViewModel.registerUser("qwerty@123.com","qwerty").getOrAwaitValue()
        assertEquals(REGISTRATION_SUCCESSFUL, result)
    }
}