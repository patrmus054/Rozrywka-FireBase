package com.example.rozrywka_firebase.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.data.remote.Authentication
import com.example.rozrywka_firebase.di.ApplicationModule
import com.example.rozrywka_firebase.di.DaggerApplicationComponent
import com.example.rozrywka_firebase.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

   var authentication = Authentication(Application())

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private var viewModelJob = Job()
    private val corountineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun login(email: String, password: String) {
        //DaggerApplicationComponent.builder().applicationModule(ApplicationModule(Application())).build().inject(LoginActivity())
        corountineScope.launch {
            Log.d("niemomczasu", "test0")
            authentication.signIn(Application(),email, password)
        }
        Log.d("niemomczasu", HomeViewModel.currentUser)
        if (HomeViewModel.currentUser != "") {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = HomeViewModel.currentUser))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
 }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
