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
include(":sources:features:feature_chat:chat")
include(":sources:features:feature_chat:chat_api")
include(":sources:features:feature_home:home")
include(":sources:features:feature_home:home_api")
include(":sources:features:featureApi")
include(":sources:features:base_models")

include(":sources:core:core_api")
include(":sources:core:core_impl")
include(":sources:core:core_factory")

include(":sources:main")




