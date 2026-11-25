/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

import extension.*
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
}

extensions.extraProperties["multiloaderTypeAttribute"] = Attribute.of("multiloader.type", String::class.java)
private val expansionExtension : PlaceholderExpansionExtension = extensions.create<PlaceholderExpansionExtension>("placeholderExpansion")

java {
    toolchain.languageVersion.set(the<LibrariesForLibs>().versions.java.map(JavaLanguageVersion::of))
    withSourcesJar()
}

base {
    val moduleName : String = project.name
    base.archivesName.set(providers.gradleProperty("mod_id").map { "$it-$moduleName" })
}

tasks.named("build") {
    dependsOn(providers.gradleProperty("build_testmod").map { if (it.toBoolean()) listOf("testmodJar") else emptyList() })
}

tasks.withType<Jar>().configureEach {
    val modId : Provider<String> = providers.gradleProperty("mod_id")
    inputs.property("mod_id", modId)
    from(rootProject.layout.projectDirectory.file("LICENSE")) {
        rename { "${it}_${modId.get()}" }
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
tasks.named<Jar>("jar") {
    filesMatching(listOf("**/*.json", "**/*.mcmeta", "**/*.toml")) {
        when (file.extension.lowercase()) {
            "json", "mcmeta" -> filter(JsonMinifyReader::class.java)
            "toml" -> filter(TomlMinifyReader::class.java)
        }
    }
}

tasks.withType<ProcessResources>().configureEach {
    val taskName = this.name
    val targetFiles = expansionExtension.files.orElse(emptyList())
    val placeholderProperties = expansionExtension.properties.orElse(emptyMap())
    inputs.property("resourceFiles", targetFiles)
    inputs.property("placeholderProperties", placeholderProperties)
    eachFile {
        if ("$taskName/${relativePath.pathString}" in targetFiles.get()) expand(placeholderProperties.get())
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Jar>("testmodJar") {
    group = LifecycleBasePlugin.BUILD_GROUP
    description = "Assembles a jar archive containing the classes of the 'testmod' feature."
    archiveClassifier.set("testmod")
    from(sourceSets.named("testmod").map { it.output })
}