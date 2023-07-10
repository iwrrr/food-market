import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.apply {
                    targetSdk = 33
                    applicationId = "com.hwaryun.foodmarket"
                    versionCode = 1
                    versionName = "1.0"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("permissions").get())
                add("implementation", libs.findLibrary("timber").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("ext.junit").get())
                add("testImplementation", libs.findLibrary("espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("ui.test.junit4").get())
                add("androidTestImplementation", platform(libs.findLibrary("compose.bom").get()))
                add("debugImplementation", libs.findLibrary("ui.tooling").get())
                add("debugImplementation", libs.findLibrary("ui.test.manifest").get())
            }
        }
    }
}