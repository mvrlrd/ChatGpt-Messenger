plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}
apply<ComposePlugin>()
apply<ModuleConfigPlugin>()
apply<CommonDependenciesPlugin>()

android {
    namespace = "ru.mvrlrd.chat_settings"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}
dependencies {
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.features.featureSettings.settingsApi)
    implementation(projects.sources.features.baseModels)
}