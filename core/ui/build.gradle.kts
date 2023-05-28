plugins {
    id("forge.android.library")
    id("forge.android.compose")
}

android {
    namespace = "forge.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.kotlinx.datetime)
}