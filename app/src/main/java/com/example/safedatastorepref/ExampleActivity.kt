package com.example.safedatastorepref

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.safedatastorepref.sample.UserSettingsOperations
import com.example.safedatastorepref.sample_encrypt.UserDataOperationsEncrypt

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserSettingsOperations()
        UserDataOperationsEncrypt()
    }
}