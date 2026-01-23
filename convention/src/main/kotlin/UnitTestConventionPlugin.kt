import com.android.build.gradle.LibraryExtension
import configuration.AndroidSdk
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class UnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            with (pluginManager) {
                apply(libs.findPlugin("ksp").get().get().pluginId)
                apply("org.jetbrains.kotlin.android")

                configure<LibraryExtension>() {
                    configureKotlinAndroid(this)
                    defaultConfig.targetSdk = AndroidSdk.targetSdk
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

                dependencies {
                    dependencies {
                        // Classic unit testing
                        testImplementation(libs.findLibrary("junit").get())
                        // JUnit 4

                        // Assertions
                        testImplementation(libs.findLibrary("truth-android-test").get())
                        // Truth assertions

                        // Coroutines testing
                        testImplementation(libs.findLibrary("kotlinx-coroutines-test").get())
                        // kotlinx-coroutines-test

                        // Architecture / lifecycle testing helpers
                        testImplementation(libs.findLibrary("core-android-test").get())
                        // core-testing (LiveData, ViewModel)

                        // Mockk
                        testImplementation(libs.findLibrary("mockk").get())

                        // Flow testing
                        testImplementation(libs.findLibrary("turbine").get())
                        // For testing Kotlin Flows
                    }
                }
            }
        }
    }

}