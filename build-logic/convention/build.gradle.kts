/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "top.cyclops.forge.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin{
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradle.pluginz)
    compileOnly(libs.kotlin.gradle.pluginz)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "forge.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "forge.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "forge.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("AndroidApplicationCompose") {
            id = "forge.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("AndroidData") {
            id = "forge.android.data"
            implementationClass = "AndroidDataConventionPlugin"
        }
        register("AndroidCompose") {
            id = "forge.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("AndroidFeature") {
            id = "forge.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

    }
}
