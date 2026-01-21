import com.android.build.gradle.LibraryExtension
import configuration.AndroidSdk
import extensions.androidTestImplementation
import extensions.implementation
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("android-library")
                apply("org.jetbrains.kotlin.android")
                apply(libs.findPlugin("ksp").get().get().pluginId)
            }

            extensions.configure<LibraryExtension>() {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidSdk.targetSdk
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // Assertions library
                androidTestImplementation(libs.findLibrary("truth-android-test").get())
                // Provides Truth assertions for Android instrumented tests

                // Core Android testing framework
                androidTestImplementation(libs.findLibrary("androidx-test-core").get())
                // Provides core testing APIs like TestCoroutineDispatcher, LiveData testing helpers

                // Test runner for instrumented tests
                androidTestImplementation(libs.findLibrary("androidx-test-runner").get())
                // Required to run tests on a device or emulator

                // Test rules for Android testing
                androidTestImplementation(libs.findLibrary("androidx-test-rules").get())
                // Provides rules like ActivityScenarioRule, useful for lifecycle testing

                // Extended JUnit support for Android
                androidTestImplementation(libs.findLibrary("androidx-test-ext-junit").get())
                // Adds Android-specific extensions to JUnit (e.g., ActivityScenario support)

                // Espresso UI testing framework
                androidTestImplementation(libs.findLibrary("androidx-test-espresso-core").get())
                // For writing UI interaction tests on activities/fragments

                // Coroutines test helpers
                androidTestImplementation(libs.findLibrary("kotlinx-coroutines-test").get())
                // Provides TestCoroutineDispatcher, runTest, etc., for testing suspend functions


            }
        }
    }

}