pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mintyn Coding Test"
include(":app")
 