@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.home"
}

dependencies {

    implementation(project(":core:design-system"))
    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.runtime.ktx)

    implementation(libs.ratingbar)
}