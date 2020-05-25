package com.example.rozrywka_firebase.di

import android.content.Context
import com.example.rozrywka_firebase.data.remote.Authentication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {


    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context


    @Provides
    @Singleton
    fun provideAuthentication(): Authentication = Authentication(context)

}