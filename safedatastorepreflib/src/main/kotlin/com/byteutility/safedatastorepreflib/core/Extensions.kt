package com.byteutility.safedatastorepreflib.core

import android.content.Context

/**
 * Creates a [TypedPrefDataStore] instance for the given type [T], using the provided [name] and [serializer].
 *
 * This is an inline extension function on [Context] with a reified type parameter, allowing for type-safe
 * creation of a [TypedPrefDataStore] without manually passing the class reference.
 *
 * ⚠️ **Note:** Ensure this function is called only **once per process per `name`** to avoid a fatal exception
 * due to multiple DataStore instances using the same file name.
 *
 * @param T The type of object to store in the DataStore.
 * @param name The name of the preference file.
 * @param serializer A serializer to convert between [T] and String for storage.
 * @return A [TypedPrefDataStore] configured for the specified type [T].
 */
inline fun <reified T : Any> Context.typedPrefDataStore(
    name: String,
    serializer: TypedPrefSerializer<T>
): TypedPrefDataStore<T> {
    return TypedPrefDataStore(this, name, T::class.java, serializer)
}
