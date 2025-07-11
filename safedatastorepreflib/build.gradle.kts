plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.byteutility.safedatastorepreflib"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        aarMetadata {
            minCompileSdk = 29
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.byteutility"
            artifactId = "safedatastorepreflib"
            version = "1.2"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "localTest"
            url = uri("$rootDir/maven-repo")
        }
    }
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}