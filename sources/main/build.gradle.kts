plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ComposePlugin>()
apply<ModuleConfigPlugin>()
apply<CommonDependenciesPlugin>()

android {
    namespace = "ru.mvrlrd.main"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.features.featureChat.chatApi)
    implementation(projects.sources.features.featureHome.homeApi)
    implementation(projects.sources.features.featureSettings.settingsApi)


    implementation("com.google.accompanist:accompanist-navigation-animation:0.35.1-alpha")


}