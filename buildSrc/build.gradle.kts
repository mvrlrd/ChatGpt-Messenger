plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}



dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    implementation("com.android.tools.build:gradle:8.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")

    testImplementation(kotlin("test")) // или testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")

    // JUnit
    testImplementation("junit:junit:4.13.2")

    // Mockito
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // AndroidX Test
    testImplementation("androidx.test:core:1.6.1")
    testImplementation("androidx.test.ext:junit:1.2.1")



}

buildscript {

}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17