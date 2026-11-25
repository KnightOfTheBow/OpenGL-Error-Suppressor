plugins {
    id("multiloader-base")
    alias(libs.plugins.vanillagradle)
}

@Suppress("UNCHECKED_CAST")
val multiloaderTypeAttribute = extensions.extraProperties["multiloaderTypeAttribute"] as Attribute<String>

sourceSets {
    register("testmod") {
        compileClasspath += files(sourceSets.main.map { it.compileClasspath }, sourceSets.main.map { it.output })
        runtimeClasspath += files(sourceSets.main.map { it.runtimeClasspath }, sourceSets.main.map { it.output })
    }
}

configurations {
    consumable("commonJava") {
        description = "Directories containing compiled class files for main."
        attributes { attribute(multiloaderTypeAttribute, "common-java") }
        outgoing.artifact(sourceSets.main.flatMap { it.java.classesDirectory }) {
            builtBy(sourceSets.main.map { it.compileJavaTaskName })
        }
    }
    consumable("commonResources") {
        description = "Directories containing assembled resource files for main."
        attributes { attribute(multiloaderTypeAttribute, "common-resources") }
        outgoing.artifact(sourceSets.main.map { it.output.resourcesDir }) {
            builtBy(sourceSets.main.map { it.processResourcesTaskName })
        }
    }
    consumable("commonSources") {
        description = "List of source directories contained in the main source set."
        attributes { attribute(multiloaderTypeAttribute, "common-sources") }
        sourceSets.main.get().allSource.srcDirs.forEach { srcDir -> outgoing.artifact(srcDir) }
    }
    consumable("commonTestmodJava") {
        description = "Directories containing compiled class files for testmod."
        attributes { attribute(multiloaderTypeAttribute, "common-testmod-java") }
        outgoing.artifact(sourceSets.named("testmod").flatMap { it.java.classesDirectory }) {
            builtBy(sourceSets.named("testmod").map { it.compileJavaTaskName })
        }
    }
    consumable("commonTestmodResources") {
        description = "Directories containing assembled resource files for testmod."
        attributes { attribute(multiloaderTypeAttribute, "common-testmod-resources") }
        outgoing.artifact(sourceSets.named("testmod").map { it.output.resourcesDir }) {
            builtBy(sourceSets.named("testmod").map { it.processResourcesTaskName })
        }
    }
}

minecraft.version(libs.versions.minecraft.get())

dependencies {
    implementation(libs.commonmixin)
    implementation(libs.commonmixinextras)
    annotationProcessor(libs.commonmixinextras)
}

tasks.named("decompile") {
    notCompatibleWithConfigurationCache("Invocation of 'Task.project' at execution time.")
}