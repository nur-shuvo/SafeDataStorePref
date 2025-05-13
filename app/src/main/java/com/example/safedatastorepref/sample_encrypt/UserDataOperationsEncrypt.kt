package com.example.safedatastorepref.sample_encrypt

import android.util.Log
import com.byteutility.safedatastorepreflib.core.encryptedTypedPrefDataStore
import com.example.safedatastorepref.MyApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDataOperationsEncrypt {

    private val userDataStore = MyApplication.Companion.context.encryptedTypedPrefDataStore(
        "user_data",
        UserDataSerializer()
    )

    init {
        GlobalScope.launch {
            // Insert object
            val userData = UserData("John", true, 1, listOf("Book1", "Book2"))
            userDataStore.insertOrUpdate(userData)
            // Get  object
            Log.i("UserDataOperationsEncrypt", "get ${userDataStore.get().toString()}")
            // Update existing object
            userDataStore.update {
                it.copy(
                    count = 2,
                    bookList = listOf("Book3")
                )
            }
            // Get updated object
            Log.i("UserDataOperationsEncrypt", "get updated ${userDataStore.get().toString()}")
            // Delete object
            userDataStore.delete()
            Log.i(
                "UserDataOperationsEncrypt",
                "After delete get:  ${userDataStore.get().toString()}"
            )
        }

        GlobalScope.launch {
            userDataStore.getAsFlow().collect {
                Log.i("UserDataOperationsEncrypt", "get as flow: $it")
            }
        }
    }
}
