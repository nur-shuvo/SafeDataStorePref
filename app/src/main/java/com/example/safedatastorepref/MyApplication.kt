package com.example.safedatastorepref

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApplication

        val context: Context
            get() = instance.applicationContext
    }
}
