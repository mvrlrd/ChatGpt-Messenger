plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
}
apply<ComposePlugin>()
apply<ModuleConfigPlugin>()
apply<CommonDependenciesPlugin>()

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
    implementation(projects.sources.coreApi)


    // Core Mockito library
    testImplementation("org.mockito:mockito-core:4.11.0")

    // Mockito Kotlin extension for more idiomatic Kotlin code
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")

    // Kotlin standard library for testing
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")

    // Coroutines test library if you are testing coroutine-based code
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}
allOpen{
    annotation("ru.mvrlrd.core_api.annotations.Open")
}


