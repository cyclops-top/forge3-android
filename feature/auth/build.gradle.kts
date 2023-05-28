plugins {
    id("forge.android.feature")
}

android {
    namespace = "forge.feature.auth"
}

dependencies {
    implementation(project(":data:auth"))
}