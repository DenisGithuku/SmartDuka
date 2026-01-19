import com.google.devtools.ksp.gradle.KspExtension
import extensions.getLibrary
import extensions.implementation
import extensions.ksp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp") // Apply KSP plugin


            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                implementation(libs.getLibrary("koin.android"))
                implementation(libs.getLibrary("koin.compose").get())
                implementation(libs.getLibrary("koin.annotations"))
                ksp(libs.getLibrary("koin.ksp.compiler"))
            }

            extensions.configure<KspExtension> {
                // Options for KSP processor if needed

            }
        }
    }

    interface KoinExtension {
        var applyKoin: Boolean
    }
}