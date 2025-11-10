plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.savebytes.campusbuddy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.savebytes.campusbuddy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"http://192.168.0.106:8080/\"")
            buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"297955126873-n80rs5l7v9mimodcoj2829qgodmob69s.apps.googleusercontent.com\"")
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            buildConfigField("String", "BASE_URL", "\"https://api.com\"")
            buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"297955126873-n80rs5l7v9mimodcoj2829qgodmob69s.apps.googleusercontent.com\"")
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    // Add this for Hilt
    kapt {
        correctErrorTypes = true
    }

}

dependencies {

    // Core
    implementation(libs.bundles.core)

    // Testing
    implementation(libs.bundles.testing)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Network
    implementation(libs.bundles.network)

    // Dagger + Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // DataStore (Shared Pref)
    implementation(libs.datastore.preferences)

    // Firebase bom
    implementation(platform(libs.firebase.bom))

    // Google Service & Firebase
    implementation(libs.firebase.auth)
    implementation(libs.bundles.google.firebase)

    // Coroutines support
    implementation(libs.bundles.coroutine)

}