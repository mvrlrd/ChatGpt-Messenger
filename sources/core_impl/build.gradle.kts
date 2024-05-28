plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
}
apply<CommonDependenciesPlugin>()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

//    implementation("com.google.dagger:dagger:2.48")
//    ksp("com.google.dagger:dagger-compiler:2.48")
//    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.0-1.0.12")


}



