package com.byteutility.safedatastorepreflib.core

/**
 * Interface for serializing and deserializing custom types to and from [String],
 * to be used with [TypedPrefDataStore].
 *
 * Implement this interface to define how your data class or type [T] is converted
 * to a [String] for storage in DataStore, and back from a [String] when reading.
 *
 * @param T The type to be serialized and deserialized.
 */
interface TypedPrefSerializer<T> {

    /**
     * Converts the given [value] of type [T] to its [String] representation.
     *
     * @param value The value to serialize.
     * @return A [String] representing the serialized form of [value].
     */
    fun serialize(value: T): String

    /**
     * Converts the given [serialized] [String] back into an instance of type [T].
     *
     * @param serialized The serialized [String] representation.
     * @return The deserialized value of type [T].
     */
    fun deserialize(serialized: String): T
}
