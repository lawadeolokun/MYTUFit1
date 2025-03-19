pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Declare plugin versions here
        id("com.android.application") version "8.0.2"
        id("org.jetbrains.kotlin.android") version "1.8.22"
        id("androidx.navigation.safeargs.kotlin") version "2.8.8"
        id("com.google.gms.google-services") version "4.4.2"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MYTUFit"
include(":app")
