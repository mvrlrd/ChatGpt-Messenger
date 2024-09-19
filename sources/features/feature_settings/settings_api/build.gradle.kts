plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()

android {
    namespace = "ru.mvrlrd.feature_settings_api"
}

dependencies {
    api(projects.sources.features.common.featureApi)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}