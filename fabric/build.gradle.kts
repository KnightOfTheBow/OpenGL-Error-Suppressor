plugins {
    id("multiloader-loader")
    alias(libs.plugins.fabricloom)
}

placeholderExpansion {
    addFiles(sourceSets.main, "fabric.mod.json")
    addFiles(sourceSets.named("testmod"), "fabric.mod.json")
    fromGradleProperties("source_url")
}

loom {
    mods {
        create(providers.gradleProperty("mod_id").get()) {
            sourceSet(sourceSets.main.get())
            modFiles.from(configurations.named("commonJava"))
            modFiles.from(configurations.named("commonResources"))
        }
        create(providers.gradleProperty("testmod_id").get()) {
            sourceSet(sourceSets.named("testmod").get())
            modFiles.from(configurations.named("commonTestmodJava"))
            modFiles.from(configurations.named("commonTestmodResources"))
        }
    }
    runs {
        named("client") {
            client()
            displayName.set("Fabric Client")
            runDirectory.set(layout.projectDirectory.dir("runs/client"))
            systemProperties.put("fabric.log.level", "debug")
            sourceSet.set("testmod")
            generateRunConfig.set(true)
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
    implementation(libs.fabricloader)
    runtimeOnly(libs.modmenu)
}