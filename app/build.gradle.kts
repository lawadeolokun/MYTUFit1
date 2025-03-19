plugins {
    id("com.android.application")      // Android application plugin
    id("org.jetbrains.kotlin.android") // Kotlin plugin
    id("androidx.navigation.safeargs.kotlin") // Safe Args Plugin
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services") // Firebase Plugin
}

android {
    namespace = "com.example.mytufit"

    // If you want a stable compileSdk, use 34 (Android 14).
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mytufit"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Java/Kotlin compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Enable ViewBinding and Compose
    buildFeatures {
        viewBinding = true
        compose = true
    }

    // Configure the Compose compiler extension version
    composeOptions {
        // Must match your Kotlin version & Compose BOM
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {
    // Navigation (Fragment and UI)
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.8")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.8")

    // Use the Firebase BoM for consistent versions across Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))

    // Now add Firebase libraries without specifying individual versions
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-appcheck-ktx")

    // implementation("com.google.firebase:firebase-firestore-ktx")

    // Core & Lifecycle
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")

    // Compose BOM & Compose Libraries
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Other UI Libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}