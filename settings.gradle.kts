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
include(":sources:common:common-ui")
include(":sources:common:common-network")
include(":sources:common:common-navigation")
include(":sources:features:app-database")
include(":sources:features:feature-splashscreen")
include(":sources:features:feature-auth-api")
include(":sources:features:feature-auth-ui")
include(":sources:features:feature-main-container")
include(":sources:features:feature-finances-ui")
include(":sources:features:feature-profile")
include(":sources:features:feature-operations-api")
