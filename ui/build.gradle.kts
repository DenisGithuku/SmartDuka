plugins {
    alias(libs.plugins.smartduka.android.library)
    alias(libs.plugins.smartduka.compose.library)
    alias(libs.plugins.smartduka.convention.koin)
    alias(libs.plugins.smartduka.android.test)
    alias(libs.plugins.smartduka.unit.test)
}

android { namespace = "com.githukudenis.smartduka.ui" }

dependencies { implementation(project(AndroidModules.DesignSystem)) }
