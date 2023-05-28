plugins {
    id("forge.android.library")
    id("forge.kotlin.serialization")
}

android {
    namespace = "forge.data"

}

dependencies {
    api(libs.datastore)
    api(libs.datastore.preferences)
    implementation(libs.paging.core)
    implementation(project(":core:model"))
    implementation(libs.retrofit)
    implementation(libs.room.ktx)
}