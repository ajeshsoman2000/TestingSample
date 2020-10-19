package com.example.myunittestsample

import androidx.lifecycle.LiveData

class MainRepository(private val mainApiService: MainApiService) {

//    private val mainApiService: MainApiService by lazy { MainApiService() }

    fun login(userId: String, password: String): LiveData<String> {
        return mainApiService.authenticateUser(userId, password)
    }

    fun register(userId: String, password: String): LiveData<String> {
        return mainApiService.registerUser(userId, password)
    }
}