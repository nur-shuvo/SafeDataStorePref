
# SafeDataStorePref

A type-safe, Kotlin-friendly wrapper for Android's Jetpack DataStore Preferences using serialization and DSL-style configuration. Designed to simplify reading and writing custom data types without manual serialization or null checks.

**Why SafeDataStorePref?**

In my opinion, though Proto DataStore is a powerful option for data storage in Android for type safety, it can come with some development and learning overhead. The need for .proto files, code generation, and dealing with binary serialization can add complexity. Additionally, it can be rigid when handling custom data models, nullable fields, and especially when handing default values.

To address these challenges, SafeDataStorePref comes in, which offers a type-safe, Kotlin-friendly easy-to-use wrapper around Android's Jetpack DataStore Preferences.

[![](https://jitpack.io/v/nur-shuvo/SafeDataStorePref.svg)](https://jitpack.io/#nur-shuvo/SafeDataStorePref)

## Features

- ✅ Type-safe preference datastore storage
- ✅ Custom serialization support
- ✅ Encryption support backed by Android keystore
- ✅ Null-safe update operations
- ✅ Kotlin DSL-style builder
- ✅ Minimal boilerplate

## Installation

Add JitPack to your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

Then, add the dependency in your module `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.nur-shuvo:SafeDataStorePref:v1.2.0")
}
```

## Usage
<img src="photos/SafeDataStorePref-visual.png" alt="Screenshot 1" style="display: block; margin: auto;" />

### Step 1: Define your data class choosing your own serialization library (i.e Gson, Moshi, Kotlinx Serialization)

```kotlin
@Serializable
data class UserSettings(
    val userName: String,
    val isOn: Boolean,
    val count: Int
)

```

### Step 2: Implement a custom serializer

```kotlin
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

```

### Step 3: Create the DataStore

```kotlin
 private val userSettingsStore = context.typedPrefDataStore(
        "user_settings",
        UserSettingSerializer()
    )

// Optionally, if you want encrypted datastore,
private val userDataStore = MyApplication.Companion.context.encryptedTypedPrefDataStore(
        "user_data",
        UserDataSerializer()
    )
```
> **Note**  
> Creating a `DataStore` instance with a specific name should only be done **once per process**.  
> Multiple instances with the same name in the same process can lead to a **fatal exception**.

## Operations

### Insert or update

```kotlin
suspend fun saveSettings() {
    userSettingsStore.insertOrUpdate(UserSettings(isOn = true, username = "nurshuvo"))
}
```

### Update only if exists (null-safe)

```kotlin
suspend fun toggleSettings() {
    userSettingsStore.update { current ->
        current.copy(isOn = false)
    }
}
```

### Read

```kotlin
val currentSettings = userSettingsStore.get()
```

### Read as Flow

```kotlin
val settingsFlow: Flow<UserSettings?> = userSettingsStore.getAsFlow()
lifecycleScope.launch {
    userSettingsStore.getAsFlow().collect { settings ->
        // Handle the collected UserSettings object
    }
}
```

### Delete

```kotlin
suspend fun clearSettings() {
    userSettingsStore.delete()
}
```

## Developed by

[nur-shuvo](https://github.com/nur-shuvo)
