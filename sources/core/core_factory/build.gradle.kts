plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()
android {
    namespace = "ru.mvrlrd.core_factory"
}

dependencies {
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.core.coreImpl)

    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.googleMaterial)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}