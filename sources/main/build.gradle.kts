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
    implementation(projects.sources.coreApi)
    implementation(projects.sources.uiKit)
    implementation(projects.sources.home)
    implementation(projects.sources.favorites)


    implementation("com.google.accompanist:accompanist-navigation-animation:0.35.1-alpha")


}