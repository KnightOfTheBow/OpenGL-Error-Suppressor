/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

import extension.*

plugins {
    id("multiloader-base")
}

placeholderExpansion {
    fromGradleProperties("mod_id", "version", "license", "mod_name", "description", "author", "website", "issue_tracker", "testmod_id", "minimum_minecraft_version", "update_json")
}

@Suppress("UNCHECKED_CAST")
val multiloaderTypeAttribute = extensions.extraProperties["multiloaderTypeAttribute"] as Attribute<String>

// WORKAROUND: Despite ForgeGradle 7 setting up custom and mandatory repositories like minecraft.mavenizer(it), it
// seemingly doesn't auto-inject dependency repositories, preventing a single source of truth for them in the root
// settings.gradle.kts. To minimize repetition across subprojects, dependency repositories are centrally declared here.
repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/") {
        name = "SpongePowered"
        mavenContent { includeGroup("org.spongepowered") }
    }
    maven("https://maven.fabricmc.net/") {
        name = "Fabric"
        mavenContent { includeGroupAndSubgroups("net.fabricmc") }
    }
    maven("https://maven.minecraftforge.net/") {
        name = "Forge"
        mavenContent { includeGroup("net.minecraftforge") }
    }
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
        mavenContent { includeGroup("com.terraformersmc") }
    }
    mavenCentral()
    gradlePluginPortal()
}

configurations {
    register("commonJava") {
        description = "Common compiled class files for source set 'main'."
        isCanBeConsumed = false
        isCanBeResolved = true
        attributes { attribute(multiloaderTypeAttribute, "common-java") }
    }
    register("commonResources") {
        description = "Common assembled resource files for source set 'main'."
        isCanBeConsumed = false
        isCanBeResolved = true
        attributes { attribute(multiloaderTypeAttribute, "common-resources") }
    }
    register("commonSources") {
        description = "Common source directories for source set 'main'."
        isCanBeConsumed = false
        isCanBeResolved = true
        attributes { attribute(multiloaderTypeAttribute, "common-sources") }
    }
    register("commonTestmodJava") {
        description = "Common compiled class files for source set 'testmod'."
        isCanBeConsumed = false
        isCanBeResolved = true
        attributes { attribute(multiloaderTypeAttribute, "common-testmod-java") }
    }
    register("commonTestmodResources") {
        description = "Common assembled resource files for source set 'testmod'."
        isCanBeConsumed = false
        isCanBeResolved = true
        attributes { attribute(multiloaderTypeAttribute, "common-testmod-resources") }
    }
}

sourceSets {
    named("main") {
        compileClasspath += files(configurations.named("commonJava"))
        runtimeClasspath += files(configurations.named("commonJava"), configurations.named("commonResources"))
        output.dir(configurations.named("commonJava").map { it.singleFile }, "builtBy" to configurations.named("commonJava"))
    }
    register("testmod") {
        compileClasspath += files(sourceSets.main.map { it.compileClasspath }, sourceSets.main.map { it.output }, configurations.named("commonTestmodJava"))
        runtimeClasspath += files(sourceSets.main.map { it.runtimeClasspath }, sourceSets.main.map { it.output }, configurations.named("commonTestmodJava"), configurations.named("commonTestmodResources"))
        output.dir(configurations.named("commonTestmodJava").map { it.singleFile }, "builtBy" to configurations.named("commonTestmodJava"))
    }
}

dependencies {
    "commonJava"(project(":common"))
    "commonResources"(project(":common"))
    "commonSources"(project(":common"))
    "commonTestmodJava"(project(":common"))
    "commonTestmodResources"(project(":common"))
}

tasks.named<JavaCompile>("compileTestmodJava") {
    dependsOn(configurations.named("commonTestmodJava"))
}

tasks.named<Jar>("sourcesJar") {
    from(configurations.named("commonSources"))
}

tasks.named<ProcessResources>("processResources") {
    from(configurations.named("commonResources"))
}
tasks.named<ProcessResources>("processTestmodResources") {
    from(configurations.named("commonTestmodResources"))
}