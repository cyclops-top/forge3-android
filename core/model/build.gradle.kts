plugins {
    id("forge.android.library")
    id("forge.kotlin.serialization")
    id("forge.android.hilt")
}

android {
    namespace = "top.cyclops.forge.model"
}

dependencies {
    api(libs.kotlinx.datetime)
}