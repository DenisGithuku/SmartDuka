import com.android.build.api.dsl.LibraryExtension
import configuration.AndroidSdk
import extensions.androidTestImplementation
import extensions.getLibrary
import extensions.implementation
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidSdk.targetSdk
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit").get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }
            dependencies {
                androidTestImplementation(kotlin("test"))
                testImplementation(kotlin("test"))
                // androidx
                implementation(libs.getLibrary("androidx-core-ktx"))
                implementation(libs.getLibrary("androidx-appcompat"))
                androidTestImplementation(libs.getLibrary("androidx-test-ext-junit"))
                androidTestImplementation(libs.getLibrary("core-android-test"))
                androidTestImplementation(libs.getLibrary("truth-android-test"))
                androidTestImplementation(libs.getLibrary("kotlinx-coroutines-test"))
                androidTestImplementation(libs.getLibrary("androidx-test-espresso-core"))
                // timber
                implementation(libs.getLibrary("timber"))
            }
        }
    }
}