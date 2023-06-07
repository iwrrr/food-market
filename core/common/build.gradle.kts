plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}