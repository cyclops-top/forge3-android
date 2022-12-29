import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import top.cyclops.forge.build.configureAndroidCompose
import top.cyclops.forge.build.implementation
import top.cyclops.forge.build.library
import top.cyclops.forge.build.libs


class AndroidDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val libs by libs()

            pluginManager.apply {
                apply("forge.android.library")
                apply("forge.android.hilt")
                apply("forge.kotlin.serialization")
            }

            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:network"))
                implementation(project(":core:model"))
                implementation(libs.library("retrofit"))
            }
        }
    }
}
