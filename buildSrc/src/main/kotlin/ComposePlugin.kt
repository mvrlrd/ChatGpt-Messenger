import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.dependencies.apply {
            add("implementation", "androidx.core:core-ktx:$coreKtx")
            add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntime")
            add("implementation", "androidx.activity:activity-compose:$activityCompose")
            add("implementation", "androidx.compose:compose-bom:$composeBom")
            add("implementation", "androidx.compose.ui:ui:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-graphics:$composeVersion")
            add("implementation", "androidx.compose.ui:ui-tooling-preview:$composeVersion")
            add("implementation", "androidx.compose.material3:material3:$material3Version")
            add("testImplementation", "junit:junit:$junitVersion")
            add("androidTestImplementation", "androidx.test.ext:junit:$uiTestJUnit4Version")
            add("androidTestImplementation", "androidx.test.espresso:espresso-core:$espressoCoreVersion")
            add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:$uiTestJUnit4Version")
            add("debugImplementation", "androidx.compose.ui:ui-tooling")
            add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
            add("implementation", "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleViewmodelCompose")
            add("implementation", "androidx.compose.runtime:runtime-livedata:$runtimeLivedata")

        }
    }

    companion object {
       private const val coreKtx = "1.13.1"
        private const val lifecycleRuntime = "2.8.0"
        private const val activityCompose = "1.9.0"
        private const val composeVersion = "1.6.7"
        private const val material3Version = "1.2.1"
        private const val junitVersion = "4.13.2"
        private  const val espressoCoreVersion = "3.5.1"
        private const val uiTestJUnit4Version = "1.1.5"
        private const val composeBom = "2023.03.00"
        private const val lifecycleViewmodelCompose = "1.0.0-alpha07"
        private const val runtimeLivedata = "1.2.0"
    }
}
