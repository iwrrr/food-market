plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.order"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))
    implementation(project(":core:domain"))

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
}