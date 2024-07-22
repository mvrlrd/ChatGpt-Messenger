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
val navComposeVersion = "2.6.0-alpha04"
dependencies {
    implementation(projects.sources.coreApi)
    implementation(projects.sources.home)
    implementation(projects.sources.favorites)

    implementation("androidx.navigation:navigation-compose:$navComposeVersion")
    implementation("androidx.navigation:navigation-compose:2.8.0-beta05")
}