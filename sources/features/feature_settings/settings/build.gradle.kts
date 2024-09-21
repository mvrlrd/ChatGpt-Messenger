plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()

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
    implementation(projects.sources.features.common.baseModels)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    implementation(libs.bundles.composeUiBundle)
    implementation(libs.bundles.composeLifecycleBundle)
    implementation(libs.bundles.composeRuntimeBundle)
    implementation(libs.material3)
    implementation(libs.bundles.composeIntegrationBundle)
    implementation(libs.navigationCompose)

    debugImplementation(libs.bundles.debugComposeBudle)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}