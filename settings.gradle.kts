pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}



enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Companion"
include(":app")
include(":sources:core_impl")
include(":sources:core_api")
include(":sources:core")
include(":source:testMod")
include(":sources:home")
