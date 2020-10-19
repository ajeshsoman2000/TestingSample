package com.example.myunittestsample

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainApiServiceTest {

    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var mainApiService: MainApiService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(mainApiService)
    }

    @Test
    fun authenticateUserMethodCalledTest() {
        mainRepository.login("123@456.com", "123456")
        Mockito.verify(mainApiService).authenticateUser("123@456.com", "123456")
    }

    @Test
    fun registerUserMethodCalledTest() {
        mainRepository.register("123@456.com", "123456")
        Mockito.verify(mainApiService).registerUser("123@456.com", "123456")
    }
}