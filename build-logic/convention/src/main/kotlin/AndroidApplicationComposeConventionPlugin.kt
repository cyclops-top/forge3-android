/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import top.cyclops.forge.build.bundle
import top.cyclops.forge.build.configureAndroidCompose
import top.cyclops.forge.build.configureKotlinAndroid
import top.cyclops.forge.build.implementation
import top.cyclops.forge.build.library
import top.cyclops.forge.build.libs

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs by libs()
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("forge.android.hilt")

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = 33
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
            }
            dependencies{
                implementation(project(":core:common"))
                implementation(project(":core:model"))
                implementation(project(":core:ui"))
                implementation(project(":core:statistics"))
                implementation(libs.bundle("androidx.base"))
                implementation(libs.library("androidx.hilt.navigation.compose"))
                implementation(libs.library("androidx.lifecycle.runtimeCompose"))
                implementation(libs.library("androidx.lifecycle.viewModelCompose"))

                implementation(libs.library("kotlinx.coroutines.android"))
            }
        }
    }
}