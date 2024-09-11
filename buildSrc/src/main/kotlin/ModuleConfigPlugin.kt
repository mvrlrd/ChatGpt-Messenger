import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


private const val ANDROID_LIBRARY = "android-library"
private const val KOTLIN_LIBRARY = "kotlin-android"

class ModuleConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.applyPlugins()
        project.setProjectConfiguration()
//        project.dependencies{
//            add("implementation", "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_STDLIB")
//            add("implementation", "androidx.core:core-ktx:$CORE_KTX" )
//            add("testImplementation","junit:junit:$JUNIT")
//            add("androidTestImplementation","androidx.test.ext:junit:$JUNIT_EXT")
//            add("androidTestImplementation","androidx.test.espresso:espresso-core:$ESPRESSO_CORE")
//        }
    }

    private fun Project.applyPlugins() {

        apply {
            plugin(ANDROID_LIBRARY)
            plugin(KOTLIN_LIBRARY)
        }
    }

    private fun Project.setProjectConfiguration() {
        android().apply {
            compileSdk = ProjectConfig.compileSdk
            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            kotlin().apply {
                jvmToolchain {
                    languageVersion.set(JavaLanguageVersion.of(17))
                }
                jvmToolchain(17)
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile(ProjectConfig.defaultProguardFile),
                        ProjectConfig.proguardRulesPro
                    )
                }
            }

        }
    }

    private fun Project.kotlin(): KotlinAndroidProjectExtension {
        return extensions.getByType(KotlinAndroidProjectExtension::class.java)
    }

    private fun Project.android(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }
//    companion object{
//        private const val KOTLIN_STDLIB = "1.9.24"
//        private const val CORE_KTX = "1.13.1"
//
//        private const val JUNIT = "4.13.2"
//        private const val JUNIT_EXT = "1.2.1"
//        private const val ESPRESSO_CORE = "3.6.1"
//    }
}