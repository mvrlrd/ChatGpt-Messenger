plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.mvrlrd.companion"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.mvrlrd.companion"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.sources.main)
    implementation(projects.sources.core.coreApi)
    implementation(projects.sources.core.coreFactory)
    implementation(projects.sources.features.featureChat.chat)
    implementation(projects.sources.features.featureHome.home)
    implementation(projects.sources.features.featureSettings.settings)

    implementation(projects.sources.features.featureChat.chatApi)
    implementation(projects.sources.features.featureHome.homeApi)
    implementation(projects.sources.features.featureSettings.settingsApi)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}