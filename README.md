
# SafeDataStorePref

A type-safe, Kotlin-friendly wrapper for Android's Jetpack DataStore Preferences using serialization and DSL-style configuration. Designed to simplify reading and writing custom data types without manual serialization or null checks.

[![](https://jitpack.io/v/nur-shuvo/SafeDataStorePref.svg)](https://jitpack.io/#nur-shuvo/SafeDataStorePref)

---

## Features

- ✅ Type-safe preference storage
- ✅ Custom serialization support
- ✅ Null-safe update operations
- ✅ Kotlin DSL-style builder
- ✅ Minimal boilerplate

---

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
    implementation("com.github.nur-shuvo:SafeDataStorePref:v1.0")
}
```

---

## Usage

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

```

---

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
```

### Delete

```kotlin
suspend fun clearSettings() {
    userSettingsStore.delete()
}
```

## Developed by

[nur-shuvo](https://github.com/nur-shuvo)
