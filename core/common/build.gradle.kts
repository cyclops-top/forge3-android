plugins {
    id("forge.android.library")
}

android {
    namespace = "forge.common"
}

dependencies {
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.base)
}
