plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.foodmarket"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:datasource"))

    implementation(project(":features:onboarding"))
    implementation(project(":features:login"))
    implementation(project(":features:register"))
    implementation(project(":features:home"))
    implementation(project(":features:search"))
    implementation(project(":features:transaction"))
    implementation(project(":features:profile"))
    implementation(project(":features:cart"))
    implementation(project(":features:food-detail"))
    implementation(project(":features:transaction-detail"))

    implementation(libs.splash.screen)
}