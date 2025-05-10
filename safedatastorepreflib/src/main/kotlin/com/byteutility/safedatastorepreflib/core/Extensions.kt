package com.byteutility.safedatastorepreflib.core

import android.content.Context

inline fun <reified T : Any> Context.typedPrefDataStore(
    name: String,
    serializer: TypedPrefSerializer<T>
): TypedPrefDataStore<T> {
    return TypedPrefDataStore(this, name, T::class.java, serializer)
}