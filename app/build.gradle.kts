import configuration.AndroidSdk
import extensions.implementation

plugins {
    alias(libs.plugins.smartduka.android.application)
    alias(libs.plugins.smartduka.compose.application)
    alias(libs.plugins.smartduka.firebase.application)
    alias(libs.plugins.smartduka.convention.koin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
}

android {
    namespace = AndroidSdk.namespace

    defaultConfig {
        applicationId = AndroidSdk.applicationId
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(AndroidModules.Core.DesignSystem))
}
