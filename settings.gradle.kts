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
include(":sources:splashscreen")
include(":sources:auth-ui")
include(":sources:common-ui")
include(":sources:main")
include(":sources:common-network")
include(":sources:auth-api")
include(":sources:app-database")
