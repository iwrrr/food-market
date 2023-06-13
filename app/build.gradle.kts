plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.foodmarket"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
    implementation(project(":features:signin"))
    implementation(project(":features:signup"))
    implementation(project(":features:home"))
    implementation(project(":features:order"))
    implementation(project(":features:profile"))
    implementation(project(":features:food_detail"))
    implementation(project(":features:payment"))

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
    implementation(libs.navigation.animation)
    implementation(libs.splash.screen)
}