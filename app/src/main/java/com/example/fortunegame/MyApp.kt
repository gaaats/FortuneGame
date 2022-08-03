package com.example.fortunegame

import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}