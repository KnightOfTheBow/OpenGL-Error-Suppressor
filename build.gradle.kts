plugins {
    alias(libs.plugins.licenser)
    alias(libs.plugins.vanillagradle) apply false
    alias(libs.plugins.fabricloom) apply false
    alias(libs.plugins.forgegradle) apply false
    alias(libs.plugins.moddevgradle) apply false
}

license {
    header(layout.projectDirectory.file("HEADER"))
    properties {
        set("year", providers.gradleProperty("year").get().trim())
        set("author", providers.gradleProperty("author").get().trim())
        set("license", providers.gradleProperty("license").get().trim())
    }
    style.put("toml", "HASH")
    include("**/*.java", "**/*.kt", "**/*.kts", "**/*.toml")

    tasks {
        register("sources") {
            files.from(layout.projectDirectory.asFileTree.matching {
                    include("**/src/**")
                    exclude("**/build/**", "**/.gradle/**")
            })
        }
    }
}