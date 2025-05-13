package com.example.safedatastorepref.sample_encrypt

import com.byteutility.safedatastorepreflib.core.TypedPrefSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserDataSerializer : TypedPrefSerializer<UserData> {
    override fun serialize(value: UserData): String {
        return Json.encodeToString(value)
    }

    override fun deserialize(
        serialized: String,
    ): UserData {
        return Json.decodeFromString<UserData>(serialized)
    }
}
