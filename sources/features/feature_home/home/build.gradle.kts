plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.room")

    kotlin("plugin.serialization") version "1.9.0"
}

apply<ModuleConfigPlugin>()

android {
    namespace = "ru.mvrlrd.feature_home"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.features.featureHome.homeApi)
    implementation(projects.sources.features.common.baseModels)

    implementation(libs.bundles.composeUiBundle)
    implementation(libs.bundles.composeLifecycleBundle)
    implementation(libs.bundles.composeRuntimeBundle)
    implementation(libs.material3)
    implementation(libs.bundles.composeIntegrationBundle)
    implementation(libs.navigationCompose)

    debugImplementation(libs.bundles.debugComposeBudle)

    implementation(libs.bundles.roomDeps)
    ksp(libs.androidxRoomCompiler)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.uiTest)

    testImplementation (libs.mockitoCoreV2847)
}