package com.example.safedatastorepref.sample

import android.util.Log
import com.byteutility.safedatastorepreflib.core.typedPrefDataStore
import com.example.safedatastorepref.MyApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserSettingsOperations {

    private val userSettingsStore = MyApplication.Companion.context.typedPrefDataStore(
        "user_settings",
        UserSettingSerializer()
    )

    init {
        GlobalScope.launch {
            // Insert object
            val userSettings = UserSettings("John", true, 1)
            userSettingsStore.insertOrUpdate(userSettings)
            // Get  object
            Log.i("UserSettingsOperations", "get ${userSettingsStore.get().toString()}")
            // Update existing object
            userSettingsStore.update {
                it.copy(count = 2)
            }
            // Get updated object
            Log.i("UserSettingsOperations", "get updated ${userSettingsStore.get().toString()}")
            // Delete object
            userSettingsStore.delete()
            Log.i(
                "UserSettingsOperations",
                "After delete get:  ${userSettingsStore.get().toString()}"
            )
        }

        GlobalScope.launch {
            userSettingsStore.getAsFlow().collect {
                Log.i("UserSettingsOperations", "get as flow: $it")
            }
        }
    }
}
