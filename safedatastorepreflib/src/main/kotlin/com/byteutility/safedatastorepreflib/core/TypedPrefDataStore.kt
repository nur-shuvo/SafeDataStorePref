package com.byteutility.safedatastorepreflib.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * A typed wrapper over DataStore to store and retrieve an object of type [T] using a custom serializer.
 *
 * @param T The type of object to store in the DataStore.
 * @param context The context used to access the DataStore.
 * @param prefFileName The name of the preference file.
 * @param clazz The class of the type [T].
 * @param typedPrefSerializer Serializer to convert [T] to/from String.
 *
 * @throws IllegalStateException If multiple instances of DataStore with the same name are created in the same process.
 */
class TypedPrefDataStore<T : Any>(
    private val context: Context,
    prefFileName: String,
    clazz: Class<T>,
    private val typedPrefSerializer: TypedPrefSerializer<T>
) {

    // Lazy delegate to provide a single DataStore instance scoped to the context
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        prefFileName
    )

    // Unique key for storing the serialized object
    private val key = stringPreferencesKey(prefFileName + clazz.name)

    /**
     * Inserts or updates the stored value with the given [value].
     *
     * @param value The value to be inserted or updated.
     */
    suspend fun insertOrUpdate(value: T) {
        context.dataStore.edit { prefs ->
            prefs[key] = typedPrefSerializer.serialize(value)
        }
    }

    /**
     * Updates the stored value by applying the given [transform] function.
     * If no existing value is found, no update is performed.
     *
     * @param transform A function that receives the current value and returns an updated value.
     */
    suspend fun update(transform: (T) -> T) {
        context.dataStore.edit { prefs ->
            val current = prefs[key]?.let { typedPrefSerializer.deserialize(it) }
            if (current != null) {
                prefs[key] = typedPrefSerializer.serialize(transform(current))
            }
        }
    }

    /**
     * Deletes the stored value, if present.
     */
    suspend fun delete() {
        context.dataStore.edit { it.remove(key) }
    }

    /**
     * Retrieves the stored value, or `null` if no value is found.
     *
     * @return The stored value or `null`.
     */
    suspend fun get(): T? {
        val prefs = context.dataStore.data.first()
        return prefs[key]?.let { typedPrefSerializer.deserialize(it) }
    }

    /**
     * Retrieves the stored value, or returns the given [default] if no value is found.
     *
     * @param default The default value to return if no value is stored.
     * @return The stored value or the [default].
     */
    suspend fun getOrDefault(default: T): T {
        val prefs = context.dataStore.data.first()
        return prefs[key]?.let { typedPrefSerializer.deserialize(it) } ?: default
    }

    /**
     * Returns a [Flow] of the stored value, emitting updates whenever the value changes.
     *
     * @return A [Flow] that emits the stored value or `null`.
     */
    fun getAsFlow(): Flow<T?> =
        context.dataStore.data.map { prefs ->
            prefs[key]?.let { typedPrefSerializer.deserialize(it) }
        }
}
