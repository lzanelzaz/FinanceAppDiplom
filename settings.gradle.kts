pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Finance App"
include(":app")
include(":sources:auth-ui")
include(":sources:common-ui")
include(":sources:common-network")
include(":sources:auth-api")
include(":sources:app-database")
include(":sources:common-navigation")
include(":sources:features:feature-finances")
include(":sources:features:mylibrary")
include(":sources:features:feature-main-container")
include(":sources:features:feature-profile")
include(":sources:features:feature-splashscreen")
