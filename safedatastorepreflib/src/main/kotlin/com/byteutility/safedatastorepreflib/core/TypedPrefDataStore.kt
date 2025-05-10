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

class TypedPrefDataStore<T : Any>(
    private val context: Context,
    prefFileName: String,
    clazz: Class<T>,
    private val typedPrefSerializer: TypedPrefSerializer<T>
) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        prefFileName
    )

    private val key = stringPreferencesKey(prefFileName + clazz.name)

    @Throws
    suspend fun insertOrUpdate(value: T) {
        context.dataStore.edit { prefs ->
            prefs[key] = typedPrefSerializer.serialize(value)
        }
    }

    @Throws
    suspend fun update(transform: (T) -> T) {
        context.dataStore.edit { prefs ->
            val current = prefs[key]?.let { typedPrefSerializer.deserialize(it) }
            if (current != null) {
                prefs[key] = typedPrefSerializer.serialize(transform(current))
            }
            // else: do nothing
        }
    }

    @Throws
    suspend fun delete() {
        context.dataStore.edit { it.remove(key) }
    }

    @Throws
    suspend fun get(): T? {
        val prefs = context.dataStore.data.first()
        return prefs[key]?.let { typedPrefSerializer.deserialize(it) }
    }

    @Throws
    suspend fun getOrDefault(default: T): T {
        val prefs = context.dataStore.data.first()
        return prefs[key]?.let { typedPrefSerializer.deserialize(it) } ?: default
    }

    @Throws
    fun getAsFlow(): Flow<T?> =
        context.dataStore.data.map { prefs ->
            prefs[key]?.let { typedPrefSerializer.deserialize(it) }
        }
}
