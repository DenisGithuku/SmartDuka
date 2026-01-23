plugins {
    alias(libs.plugins.smartduka.android.library)
    alias(libs.plugins.smartduka.convention.koin)
    alias(libs.plugins.smartduka.unit.test)
}

android { namespace = "com.githukudenis.smartduka.data" }

dependencies {
    implementation(project(AndroidModules.Database))
    implementation(project(AndroidModules.Domain))
}
