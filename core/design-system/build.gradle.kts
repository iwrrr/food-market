plugins {
    id("android.lib")
}

android {
    namespace = "com.hwaryun.designsystem"
}

dependencies {
    api(libs.androidx.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.runtime.compose)
    api(libs.activity.compose)
    api(platform(libs.compose.bom))
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material)
    api(libs.material3)
    api(libs.navigation.compose)
    api(libs.navigation.runtime.ktx)

    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)

    api(libs.placeholder.material)
    api(libs.material.icons)
    api(libs.coil)
    api(libs.ratingbar)
}