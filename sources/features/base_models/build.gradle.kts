plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()
apply<ComposePlugin>()
apply<CommonDependenciesPlugin>()

android {
    namespace = "ru.mvrlrd.base_chat_home"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}
dependencies{
    implementation(projects.sources.core.coreApi)
}