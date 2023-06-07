plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.datasource"
}

dependencies {
    api(project(":core:frameworks:http"))
    implementation(libs.bundles.datastore)
    implementation(libs.converter.gson)
}