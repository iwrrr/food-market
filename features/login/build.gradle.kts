plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.login"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
    implementation(project(":core:domain"))
}