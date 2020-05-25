package com.example.rozrywka_firebase.ui.login

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
