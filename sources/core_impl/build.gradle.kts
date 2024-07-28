plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    id("androidx.room")
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
    }
    buildFeatures{
        buildConfig = true
    }
}


dependencies {
    implementation(projects.sources.coreApi)

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
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