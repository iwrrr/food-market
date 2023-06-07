plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.domain"
}

dependencies {
    api(project(":core:common"))
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
}