@file:Suppress("unused")

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import top.cyclops.forge.build.bundle
import top.cyclops.forge.build.implementation
import top.cyclops.forge.build.ksp
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
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:network"))
                implementation(project(":core:model"))
                implementation(project(":core:data"))
                implementation(libs.library("retrofit"))

                implementation(libs.bundle("room"))
                ksp(libs.library("room-compiler"))

                implementation(libs.library("paging-core"))
            }
        }
    }
}
