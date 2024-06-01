import org.gradle.api.Plugin
import org.gradle.api.Project


class CommonDependenciesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.dependencies.apply {
            add("implementation", "com.google.dagger:dagger:$DAGGER_VERSION")
            add("ksp", "com.google.dagger:dagger-compiler:$DAGGER_VERSION")
//            add("implementation", "com.google.devtools.ksp:symbol-processing-gradle-plugin:${ksp}")
//            add("implementation", "org.jetbrains.kotlin:kotlin-stdlib:$kotlinStb")
        }
    }

    companion object {
        private const val DAGGER_VERSION = "2.48"
        private const val kotlin = "1.9.0"
        private const val ksp = "1.9.0-1.0.12"
        private const val dagger = "2.48"
        private const val kotlinStb = "1.9.22"
    }
}

