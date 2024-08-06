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
include(":sources:feature_chat")
include(":sources:core_api")
include(":sources:core_impl")
include(":sources:core_factory")
include(":sources:feature_home")
include(":sources:main")
