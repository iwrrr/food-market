plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.order"
}

dependencies {
    implementation(project(":core:design-system"))
}