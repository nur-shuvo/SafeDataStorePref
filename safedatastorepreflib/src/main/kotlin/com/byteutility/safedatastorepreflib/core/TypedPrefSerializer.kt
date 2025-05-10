package com.byteutility.safedatastorepreflib.core

interface TypedPrefSerializer<T> {
    fun serialize(value: T): String
    fun deserialize(serialized: String): T
}
