package com.example.myunittestsample.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun tilHasError(errorText: String) = object : TypeSafeMatcher<View>() {
        public override fun matchesSafely(view: View): Boolean {
            return (view as TextInputLayout).error == errorText
        }
        override fun describeTo(description: Description) {
        }
    }

fun Activity.hideKeypad(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}