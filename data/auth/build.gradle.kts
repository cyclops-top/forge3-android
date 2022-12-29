plugins {
    id("forge.android.data")
}

android {
    namespace = "top.cyclops.forge.data.auth"
}

dependencies {
    implementation(project(":core:storage"))
}