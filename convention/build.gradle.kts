import org.gradle.kotlin.dsl.compileOnly

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

group = "com.githukudenis.smartduka.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
    compileOnly(libs.firebase.crashlytics.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("application-convention") {
            id = "smartduka.convention.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("library-convention") {
            id = "smartduka.convention.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("compose-application-convention") {
            id = "smartduka.convention.compose.application"
            implementationClass = "ComposeApplicationConventionPlugin"
        }
        register("compose-library-convention") {
            id = "smartduka.convention.compose.library"
            implementationClass = "ComposeLibraryConventionPlugin"
        }
        register("module-convention") {
            id = "smartduka.convention.module"
            implementationClass = "ModuleConventionPlugin"
        }
        register("koin-convention") {
            id = "smartduka.convention.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("room-convention") {
            id = "smartduka.convention.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("firebase-convention") {
            id = "smartduka.convention.firebase"
            implementationClass = "FirebaseConventionPlugin"
        }
        register("android-test-convention") {
            id = "smartduka.convention.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("unit-test-convention") {
            id = "smartduka.convention.unit.test"
            implementationClass = "UnitTestConventionPlugin"
        }
    }
}