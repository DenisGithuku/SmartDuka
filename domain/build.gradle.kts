plugins {
    alias(libs.plugins.smartduka.android.library)
    alias(libs.plugins.smartduka.convention.koin)
}

android { namespace = "com.githukudenis.smartduka.domain" }

dependencies {
    implementation(project(AndroidModules.Data))
}