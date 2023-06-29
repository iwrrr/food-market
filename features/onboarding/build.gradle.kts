plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.onboarding"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
}