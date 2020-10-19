package com.example.myunittestsample

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val ALL_FIELDS_MANDATORY = "All fields are mandatory"
const val INVALID_EMAIL_FORMAT = "User id should be and email id"
const val INVALID_PASSWORD = "Password should be minimum 6 characters"
const val VALID_CREDENTIALS = "Valid credentials"

const val USER_AUTHENTICATED = "User authenticated"
const val INVALID_CREDENTIALS = "Invalid Credentials"
const val USER_NOT_FOUND = "No such user"

const val REGISTRATION_SUCCESSFUL = "User successfully registered"

class MainViewModel( private val mainRepository: MainRepository): ViewModel() {

//    private val mainRepository: MainRepository by lazy { MainRepository() }

    fun validateUser(userId: String, password: String): LiveData<String> {

        val validateUserLiveData = MutableLiveData<String>()

        when {
            userId.isEmpty() || password.isEmpty() -> validateUserLiveData.value = ALL_FIELDS_MANDATORY
            !Patterns.EMAIL_ADDRESS.matcher(userId).matches() -> validateUserLiveData.value = INVALID_EMAIL_FORMAT
            password.length < 6 -> validateUserLiveData.value = INVALID_PASSWORD
            else -> validateUserLiveData.value = VALID_CREDENTIALS
        }
        return validateUserLiveData
    }

    fun authenticateUser(userId: String, password: String): LiveData<String> {
        return mainRepository.login(userId, password)
    }

    fun registerUser(userId: String, password: String): LiveData<String> {
        return mainRepository.register(userId, password)
    }
}