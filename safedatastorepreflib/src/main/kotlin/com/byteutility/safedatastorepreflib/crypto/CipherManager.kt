package com.byteutility.safedatastorepreflib.crypto

interface CipherManager {

    @Throws(Exception::class)
    fun encrypt(inputText: String): String

    @Throws(Exception::class)
    fun decrypt(data: String): String
}
