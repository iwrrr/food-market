plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.common"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.bundles.datastore)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}