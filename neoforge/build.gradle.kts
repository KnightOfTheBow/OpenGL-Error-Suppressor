import org.slf4j.event.Level.DEBUG

plugins {
    id("multiloader-loader")
    alias(libs.plugins.moddevgradle)
}

placeholderExpansion {
    addFiles(sourceSets.main, "META-INF/neoforge.mods.toml")
    addFiles(sourceSets.named("testmod"), "META-INF/neoforge.mods.toml")
    fromGradleProperties("license_url")
}

neoForge {
    version = libs.versions.neoforge.get()
    mods {
        register(providers.gradleProperty("mod_id").get()) {
            sourceSet(sourceSets.main.get())
        }
        register(providers.gradleProperty("testmod_id").get()) {
            sourceSet(sourceSets.named("testmod").get())
        }
    }
    runs {
        register("client") {
            client()
            ideName.convention("NeoForge Client ($path)")
            gameDirectory.convention(layout.projectDirectory.dir("runs/client"))
            logLevel.set(DEBUG)
            systemProperty("forge.logging.markers", "REGISTRIES")
            sourceSet.set(sourceSets.named("testmod").get())
        }
    }
}