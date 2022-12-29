plugins {
    id("forge.android.library")
    id("forge.android.compose")
}

android {
    namespace = "top.cyclops.forge.ui"
}

dependencies {
    implementation(project(":core:common"))
}