plugins {
    alias(libs.plugins.smartduka.android.library)
    alias(libs.plugins.smartduka.convention.koin)
    alias(libs.plugins.smartduka.android.room)
    alias(libs.plugins.smartduka.android.test)
}

android { namespace = "com.githukudenis.smartduka.database" }

dependencies { implementation(libs.findLibrary("androidx-room-testing").get()) }
