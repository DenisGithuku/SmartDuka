plugins {
    alias(libs.plugins.smartduka.android.library)
    alias(libs.plugins.smartduka.convention.koin)
}

android { namespace = "com.githukudenis.smartduka.data" }

dependencies { implementation(project(AndroidModules.Database)) }
