plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}
apply<ComposePlugin>()
apply<ModuleConfigPlugin>()
apply<CommonDependenciesPlugin>()
android {
    namespace = "ru.mvrlrd.featureapi"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

}