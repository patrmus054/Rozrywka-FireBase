package com.example.rozrywka_firebase.data.remote

import android.R.attr
import android.app.Activity
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.rozrywka_firebase.ui.home.HomeViewModel
import com.example.rozrywka_firebase.ui.login.LoggedInUserView
import com.example.rozrywka_firebase.ui.login.LoginResult
import com.example.rozrywka_firebase.utility.Result
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


private lateinit var mAuth: FirebaseAuth

class Authentication @Inject constructor(context: Context) {

    init{
        mAuth = FirebaseAuth.getInstance()
        val TAG = "AuthenticationClass"
    }

    fun isSignedIn(user: FirebaseUser): Boolean = mAuth.currentUser != null
    fun signUp(context: Context, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                Activity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
//    override suspend fun login(username: String, password: String): Result<LoggedInUser> {
//        val result = dataSource.login(username, password)
//
//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//        return result
//    }
    suspend fun signIn(context: Context, email: String, password: String) {
        Log.d(TAG, "test1")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()
            ) { task ->
                Log.d(TAG, "test2")
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d(TAG, "test3")
                    HomeViewModel.currentUser = email
                    val user = mAuth.currentUser
                    Log.d(TAG, "test5")
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                    Log.d(TAG, "test6")
                }
                Log.d(TAG, "test7")
            }
    }

    fun getCurrentUser(): FirebaseUser? = mAuth.currentUser
}