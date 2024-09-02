plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
}
apply<ModuleConfigPlugin>()

android {
    namespace = "ru.mvrlrd.feature_chat"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}
dependencies {
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.features.baseModels)
    implementation(projects.sources.features.featureChat.chatApi)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    implementation(libs.bundles.composeUiBundle)
    implementation(libs.bundles.composeLifecycleBundle)
    implementation(libs.bundles.composeRuntimeBundle)
    implementation(libs.material3)

    implementation(libs.bundles.composeIntegrationBundle)
    implementation(libs.navigationCompose)
    implementation(libs.lifecycleRuntime)

    debugImplementation(libs.bundles.debugComposeBudle)

    implementation(libs.accompanistNavigationAnimation)

    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoKotlin)
    testImplementation(libs.kotlinTest)
    testImplementation(libs.kotlinxCoroutinesTest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}
allOpen{
    annotation("ru.mvrlrd.core_api.annotations.Open")
}


