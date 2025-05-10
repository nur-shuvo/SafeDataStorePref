package com.example.safedatastorepref

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userName: String,
    val isOn: Boolean,
    val count: Int
)
