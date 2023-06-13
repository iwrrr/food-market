plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.payment"
}

dependencies {
    implementation(project(":core:design-system"))
}