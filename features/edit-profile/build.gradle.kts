plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.edit_profile"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
    implementation(project(":core:domain"))
    implementation(libs.bundles.cameraX)
}