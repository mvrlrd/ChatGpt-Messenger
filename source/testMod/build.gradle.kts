plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}
apply<CommonDependenciesPlugin>()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
//        implementation("com.google.dagger:dagger:2.48")
//    ksp("com.google.dagger:dagger-compiler:2.48")
//    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.0-1.0.12")
}