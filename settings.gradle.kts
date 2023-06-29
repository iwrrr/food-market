pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "FoodMarket"
include(":app")
include(":core:common")
include(":core:design-system")
include(":core:frameworks:database")
include(":core:frameworks:http")

include(":features:home")
include(":features:transaction")
include(":features:profile")
include(":features:cart")
include(":features:login")
include(":features:register")
include(":core:datasource")
include(":core:domain")
include(":features:food-detail")
include(":features:search")
include(":features:transaction-detail")
include(":features:onboarding")
