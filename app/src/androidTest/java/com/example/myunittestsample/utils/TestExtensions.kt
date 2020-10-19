package com.example.myunittestsample.utils

import android.view.View
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
