plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.http"
}

dependencies {
    api(project(":core:common"))
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
}