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
    implementation(project(":features:login"))
    implementation(project(":features:register"))
    implementation(project(":features:home"))
    implementation(project(":features:search"))
    implementation(project(":features:transaction"))
    implementation(project(":features:profile"))
    implementation(project(":features:cart"))
    implementation(project(":features:food-detail"))
    implementation(project(":features:transaction-detail"))

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