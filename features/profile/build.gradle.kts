plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.profile"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
    implementation(project(":core:domain"))
}