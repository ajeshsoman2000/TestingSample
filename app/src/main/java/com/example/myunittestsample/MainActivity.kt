package com.example.myunittestsample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import com.example.myunittestsample.utils.SimpleIdlingResource
import com.example.myunittestsample.utils.hideKeypad
import com.example.myunittestsample.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(MainApiService()))
        ).get(MainViewModel::class.java)
    }

    private var isLogin = true

    private var idlingResource: SimpleIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            this.hideKeypad(etPassword)
            showProgressBar()
            isLogin = true
            validateInputAndProceed()
        }

        btnRegister.setOnClickListener {
            this.hideKeypad(etPassword)
            showProgressBar()
            isLogin = false
            validateInputAndProceed()
        }

        etUserId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               clearError()
            }

        })
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearError()
            }

        })
    }

    private fun clearError() {
        tilUserID.error = ""
        tilPassword.error = ""
    }

    private fun initiateLogin(userId: String, password: String) {
        mainViewModel.authenticateUser(userId, password).observe(this, Observer {
            idlingResource?.setIdleState(true)
            hideProgressBar()
            when(it) {
                INVALID_CREDENTIALS -> showToast(INVALID_CREDENTIALS)
                USER_NOT_FOUND -> showToast(USER_NOT_FOUND)
                USER_AUTHENTICATED -> startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("user", userId)
                })
            }
        })
    }

    private fun registerUser(userId: String, password: String) {
        idlingResource?.setIdleState(true)
        mainViewModel.registerUser(userId, password).observe(this, Observer {
            hideProgressBar()
            showToast(it)
        })
    }

    private fun validateInputAndProceed() {
        mainViewModel.validateUser(etUserId.text.toString().trim(), etPassword.text.toString().trim()).observe(this, Observer {
            when(it) {
                INVALID_EMAIL_FORMAT -> {
                    hideProgressBar()
                    tilUserID.error = INVALID_EMAIL_FORMAT
                }
                INVALID_PASSWORD -> {
                    hideProgressBar()
                    tilPassword.error = INVALID_PASSWORD
                }
                ALL_FIELDS_MANDATORY -> {
                    hideProgressBar()
                    if (etUserId.text.toString().trim().isEmpty()) {
                        tilUserID.error = ALL_FIELDS_MANDATORY
                    }
                    if (etPassword.text.toString().trim().isEmpty()) {
                        tilPassword.error = ALL_FIELDS_MANDATORY
                    }
                }
                VALID_CREDENTIALS -> {
                    idlingResource?.setIdleState(false)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isLogin) {
                            initiateLogin(
                                etUserId.text.toString().trim(),
                                etPassword.text.toString().trim()
                            )
                        } else {
                            registerUser(
                                etUserId.text.toString().trim(),
                                etPassword.text.toString().trim()
                            )
                        }
                    },3000)
                }
            }
        })
    }

    private fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }


    @VisibleForTesting
    @NonNull
    fun getIdlingResource(): IdlingResource? {
        if (idlingResource == null) {
            idlingResource = SimpleIdlingResource()
        }
        return idlingResource
    }
}