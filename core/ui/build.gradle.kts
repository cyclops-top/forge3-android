plugins {
    id("forge.android.library")
    id("forge.android.compose")
}

android {
    namespace = "forge.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:statistics"))
    implementation(libs.kotlinx.datetime)
}