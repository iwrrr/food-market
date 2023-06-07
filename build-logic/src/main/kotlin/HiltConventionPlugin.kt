import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", (libs.findLibrary("hilt.android").get()))
                add("kapt", (libs.findLibrary("hilt.android.compiler").get()))
                add("implementation", (libs.findLibrary("hilt.navigation.compose").get()))
            }
        }
    }
}