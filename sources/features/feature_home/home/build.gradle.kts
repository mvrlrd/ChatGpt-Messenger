plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.room")

    kotlin("plugin.serialization") version "1.9.0"
}

apply<ModuleConfigPlugin>()
apply<ComposePlugin>()

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
    implementation(projects.sources.features.featureChat.chatApi)
    implementation(projects.sources.features.baseModels)



    implementation(libs.bundles.roomDeps)
    ksp(libs.androidxRoomCompiler)

//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)


    testImplementation ("org.mockito:mockito-core:2.8.47")
}