package com.example.myunittestsample

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainApiService {

    private var users = mutableMapOf(
        "123@456.com" to "123456",
        "test@123.com" to "456789",
        "test@456.com" to "147258"
    )

    fun authenticateUser(username: String, password: String): LiveData<String> {
        val authLivedata = MutableLiveData<String>()
        when {
            users.containsKey(username) && users[username] == password -> {
                authLivedata.value = USER_AUTHENTICATED
            }
            users.containsKey(username) && users[username] != password -> {
                authLivedata.value = INVALID_CREDENTIALS
            }
            !users.containsKey(username) -> {
                authLivedata.value = USER_NOT_FOUND
            }
        }
        return authLivedata
    }

    fun registerUser(username: String, password: String): LiveData<String> {
        val registerLiveData = MutableLiveData<String>()
        users[username] = password
        registerLiveData.value = REGISTRATION_SUCCESSFUL
        return registerLiveData
    }
}