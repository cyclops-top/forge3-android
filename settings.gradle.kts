pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven(uri("https://androidx.dev/storage/compose-compiler/repository/"))
    }
}

rootProject.name = "forge"
include(":app")
include(":core:common")
include(":core:network")
include(":core:model")
include(":core:statistics")

include(":core:ui")
include(":core:data")

include(":data:auth")
include(":data:project")
include(":data:version")
include(":data:system")
include(":plugins:countly")


include(":feature:auth")
include(":feature:project")
include(":feature:download")
