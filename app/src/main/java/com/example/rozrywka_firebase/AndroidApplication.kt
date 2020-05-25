package com.example.rozrywka_firebase

import android.app.Application
import com.example.rozrywka_firebase.di.ApplicationComponent
import com.example.rozrywka_firebase.di.ApplicationModule
import com.example.rozrywka_firebase.di.DaggerApplicationComponent
import leakcanary.LeakCanary

class AndroidApplication: Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary
    }

}