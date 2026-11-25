plugins {
    id("multiloader-loader")
    alias(libs.plugins.forgegradle)
    alias(libs.plugins.jarjar)
}

placeholderExpansion {
    addFiles(sourceSets.main, "pack.mcmeta", "META-INF/mods.toml")
    addFiles(sourceSets.named("testmod"), "pack.mcmeta", "META-INF/mods.toml")
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.minecraftLibsMaven)
}

sourceSets {
    named("main") {
        runtimeClasspath += sourceSets.named("testmod").get().output
    }
}

jarJar.register()

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:${libs.versions.minecraft.get()}-${libs.versions.forge.get()}"))
    implementation(libs.commonmixin)
    implementation(libs.forgemixinextras)
    "jarJar"(libs.forgemixinextras)
}

minecraft {
    mappings("official", libs.versions.minecraft.get())
    runs {
        register("client") {
            workingDir.convention(layout.projectDirectory.dir("runs/client"))
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("forge.logging.markers", "REGISTRIES")
            args(
                "-mixin.config=${providers.gradleProperty("mod_id").get()}.mixins.json",
                "-mixin.config=${providers.gradleProperty("testmod_id").get()}.mixins.json"
            )
            mods {
                register(providers.gradleProperty("mod_id").get()) {
                    sources(sourceSets.main.get())
                }
                register(providers.gradleProperty("testmod_id").get()) {
                    sources(sourceSets.named("testmod").get())
                }
            }
        }
    }
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("slim")
    manifest.attributes("MixinConfigs" to "${providers.gradleProperty("mod_id").get()}.mixins.json")
}
tasks.named<Jar>("jarJar").configure { archiveClassifier.set("") }
tasks.named<Jar>("testmodJar") { manifest.attributes("MixinConfigs" to "${providers.gradleProperty("testmod_id").get()}.mixins.json") }