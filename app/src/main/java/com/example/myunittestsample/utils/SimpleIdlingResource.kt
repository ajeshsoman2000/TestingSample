package com.example.myunittestsample.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.atomic.AtomicBoolean


class SimpleIdlingResource : IdlingResource {

    private var callback: ResourceCallback? = null

    // Idleness is controlled with this boolean.
    private var isIdleNow =
        AtomicBoolean(true)

    override fun getName(): String = this.javaClass.name


    override fun isIdleNow(): Boolean = isIdleNow()

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.callback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        this.isIdleNow.set(isIdleNow)
        if (isIdleNow && callback != null) {
            callback?.onTransitionToIdle()
        }
    }
}