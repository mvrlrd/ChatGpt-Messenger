plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

apply<ModuleConfigPlugin>()
android {
    namespace = "ru.mvrlrd.featureapi"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)

    implementation(libs.composeUi)
    implementation(libs.navigationCompose)
}