plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    id("androidx.room")
}

apply<ModuleConfigPlugin>()
//apply<RoomDependenciesPlugin>()
android {
    namespace = "ru.mvrlrd.core_impl"
    room {
        schemaDirectory("$projectDir/schemas")
    }
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${property("BASE_URL")}\"")
        buildConfigField("String", "API_KEY", "\"${property("API_KEY")}\"")
        buildConfigField("String", "FOLDER_ID", "\"${property("FOLDER_ID")}\"")
    }
    buildFeatures{
        buildConfig = true
    }
}


dependencies {
    implementation(projects.sources.core.coreApi)
    //retrofit
    implementation(libs.bundles.retrofitDeps)
    implementation(libs.kotlinxSerializationJson)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)
    //room
    implementation(libs.bundles.roomDeps)
    ksp(libs.androidxRoomCompiler)
}