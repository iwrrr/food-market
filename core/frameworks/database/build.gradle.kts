plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.database"
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}
