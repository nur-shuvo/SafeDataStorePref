package com.example.safedatastorepref.sample_encrypt

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userName: String,
    val isOn: Boolean,
    val count: Int,
    val bookList: List<String>
)
