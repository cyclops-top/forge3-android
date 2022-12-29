import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import top.cyclops.forge.build.bundle
import top.cyclops.forge.build.configureAndroidCompose
import top.cyclops.forge.build.implementation
import top.cyclops.forge.build.library
import top.cyclops.forge.build.libs


@Suppress("unused")
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs by libs()
            pluginManager.apply {
                apply("forge.android.library")
                apply("forge.android.hilt")
            }

            extensions.getByType<LibraryExtension>().apply {
                configureAndroidCompose(this)
            }

            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:model"))
                implementation(project(":core:ui"))

                implementation(libs.bundle("androidx.base"))
                implementation(libs.library("coil.kt"))
                implementation(libs.library("coil.kt.compose"))

                implementation(libs.library("androidx.hilt.navigation.compose"))

                implementation(libs.library("androidx.lifecycle.runtimeCompose"))
                implementation(libs.library("androidx.lifecycle.viewModelCompose"))
                implementation(libs.library("navigation.compose"))

                implementation(libs.library("kotlinx.coroutines.android"))
            }
        }
    }
}
