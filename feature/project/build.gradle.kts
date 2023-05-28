plugins {
    id("forge.android.feature")
}

android {
    namespace = "forge.feature.project"
}

dependencies {
    implementation(project(":data:project"))
    implementation(project(":data:version"))
}