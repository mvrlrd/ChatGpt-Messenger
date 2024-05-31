plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply<ModuleConfigPlugin>()
apply<ComposePlugin>()
android {
    namespace = "ru.mvrlrd.home2"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

}