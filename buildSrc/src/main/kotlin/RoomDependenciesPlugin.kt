import org.gradle.api.Plugin
import org.gradle.api.Project

class RoomDependenciesPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.dependencies.apply {
            add("implementation", "androidx.room:room-runtime::$roomVersion")
            add("ksp", "androidx.room:room-compiler:$roomVersion")
            add("implementation", "androidx.room:room-ktx:$roomVersion")
            add("annotationProcessor", "androidx.room:room-compiler:$roomVersion")
        }
    }

    companion object {
        private const val roomVersion = "2.6.1"
    }
}
