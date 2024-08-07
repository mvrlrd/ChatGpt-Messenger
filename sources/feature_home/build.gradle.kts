plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.room")

    kotlin("plugin.serialization") version "1.9.0"
}
apply<CommonDependenciesPlugin>()
apply<ModuleConfigPlugin>()
apply<ComposePlugin>()
//apply<RoomDependenciesPlugin>()

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
    implementation(projects.sources.coreApi)
    implementation(projects.sources.featureHomeApi)
    implementation(projects.sources.featureChatApi)
    implementation(projects.sources.baseChatHome)
//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    testImplementation ("org.mockito:mockito-core:2.8.47")
}