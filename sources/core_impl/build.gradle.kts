plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    id("androidx.room")
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}
apply<CommonDependenciesPlugin>()
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
    implementation(projects.sources.coreApi)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
//    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:1.5.0")
    // OkHttp 4.x
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Для логирования HTTP-запросов (опционально)
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    implementation("io.ktor:ktor-client-core:2.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.3")
    implementation("io.ktor:ktor-client-cio:2.0.3") // или другой движок клиента, например, OkHttp
    implementation("io.ktor:ktor-client-content-negotiation:2.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

}