pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "SpongePowered"
            mavenContent { includeGroup("org.spongepowered") }
        }
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
            mavenContent { includeGroupAndSubgroups("net.fabricmc") }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "SpongePowered"
            mavenContent { includeGroup("org.spongepowered") }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "OpenGL-Error-Suppressor"
include("common", "fabric", "forge", "neoforge")