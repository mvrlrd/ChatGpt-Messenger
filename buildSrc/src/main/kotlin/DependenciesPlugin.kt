import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.dependencies.apply {
            add("implementation", "androidx.core:core-ktx:$CORE_KTX")
            add("implementation", "androidx.appcompat:appcompat:$APPCOMPAT")
            add("implementation", "com.google.android.material:material:$MATERIAL")
        }
    }

    companion object {
        private const val CORE_KTX = "1.9.0"
        private const val APPCOMPAT = "1.6.1"
        private const val MATERIAL = "1.12.0"
    }
}