plugins {
    id("forge.android.library")
    id("forge.kotlin.serialization")
}

android {
    namespace = "top.cyclops.forge.storage"
}

dependencies {
    api(libs.datastore)
    api(libs.datastore.preferences)
}