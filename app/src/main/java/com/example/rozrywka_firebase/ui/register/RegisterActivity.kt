package com.example.rozrywka_firebase.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.napomocinzynierom.ui.register.RegisterFragment
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.utility.addFragment

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        addFragment(R.id.register_content_frame, ::RegisterFragment)

    }
}