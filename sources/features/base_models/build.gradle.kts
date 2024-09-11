plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()

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

    implementation(libs.composeUi)
    implementation(libs.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)
}