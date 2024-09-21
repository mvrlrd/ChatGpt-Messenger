plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    id("androidx.room")

}

apply<ModuleConfigPlugin>()

android {
    namespace = "ru.mvrlrd.core_api"
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.googleMaterial)
    implementation(libs.kotlinxSerializationJson)

    implementation(libs.bundles.roomDeps)
    ksp(libs.androidxRoomCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}