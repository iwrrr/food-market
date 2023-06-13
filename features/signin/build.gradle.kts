plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.signin"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:domain"))
}