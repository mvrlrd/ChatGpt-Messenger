plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()

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

    implementation(libs.bundles.composeUiBundle)
    implementation(libs.material3)
    implementation(libs.accompanistNavigationAnimation)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}