plugins {
    id("forge.android.feature")
}

android {
    namespace = "top.cyclops.forge.feature.auth"
}

dependencies {
    implementation(project(":data:auth"))
}