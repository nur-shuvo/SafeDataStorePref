package com.example.safedatastorepref

import com.byteutility.safedatastorepreflib.core.TypedPrefSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserSettingSerializer : TypedPrefSerializer<UserSettings> {
    override fun serialize(value: UserSettings): String {
        return Json.encodeToString(value)
    }

    override fun deserialize(
        serialized: String,
    ): UserSettings {
        return Json.decodeFromString<UserSettings>(serialized)
    }
}
