package com.example.rozrywka_firebase.di

import com.example.napomocinzynierom.ui.register.RegisterFragment
import com.example.rozrywka_firebase.AndroidApplication
import com.example.rozrywka_firebase.ui.home.EditItemFragment
import com.example.rozrywka_firebase.ui.home.HomeActivity
import com.example.rozrywka_firebase.ui.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(application: HomeActivity)
   //fun inject(routeActivity: RouteActivity)
    fun inject(registerFragment: RegisterFragment)
    fun inject(editItemFragment: EditItemFragment)
    fun inject(loginActivity: LoginActivity)
}