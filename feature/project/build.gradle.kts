plugins {
    id("forge.android.feature")
}

android {
    namespace = "top.cyclops.forge.feature.project"
}

dependencies {
    implementation(project(":data:project"))
}