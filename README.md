# OpenGL Error Suppressor
![Available for Fabric](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/fabric_vector.svg)
![Available for Forge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/forge_vector.svg)
![Available for NeoForge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/neoforge_vector.svg)
![Available for Quilt](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/quilt_vector.svg)

A lightweight and clientside-only Minecraft mod that lets unique OpenGL errors log exactly only once, suppressing all subsequent spam.

_Supports all versions from 26.3 and onward._ (Tried and tested.)

> **Quilt Users:** Quilt is supported via the Fabric release. Simply download and use the Fabric `.jar`.

## Downloads
The mod is officially available to download on the following sites:
- [Modrinth](https://modrinth.com/mod/opengl-error-suppressor) (Waiting for approval)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/opengl-error-suppressor)
- [GitHub Releases](https://github.com/KnightOfTheBow/OpenGL-Error-Suppressor/releases)

## Building
1. Either download the repository ZIP and extract, or clone by doing:
   ```bash
   git clone https://github.com/KnightOfTheBow/OpenGL-Error-Suppressor.git
   cd OpenGL-Error-Suppressor
   ```
2. Run the Gradle build task:
   ```bash
   ./gradlew build
   ```
   *(Optional)* To also build the test mod (which intentionally triggers OpenGL errors for testing), run:
   ```bash
   ./gradlew testmodJar
   ```
3. Grab the compiled `.jar` files from each loader's output folder:
   - `fabric/build/libs/`
   - `forge/build/libs/`
   - `neoforge/build/libs/`

## License & Attribution
This mod is licensed under the [MIT License](LICENSE).

Credit goes to [Shut Up GL Error](https://github.com/JamCoreModding/shut-up-gl-error) (Copyright © 2025 Jamalam) for being the original inspiration for this mod.

This project uses a modified version of jaredlll08's [MultiLoader Template](https://github.com/jaredlll08/MultiLoader-Template).