plugins {
    id("forge.android.library")
    id("forge.android.hilt")
}

android {
    namespace = "forge.common"
}

dependencies {
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.base)
}
