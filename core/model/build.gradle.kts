plugins {
    id("forge.android.library")
    id("forge.kotlin.serialization")
    id("forge.android.hilt")
}

android {
    namespace = "orge.model"
}

dependencies {
    api(libs.kotlinx.datetime)
    api(libs.room.ktx)
}