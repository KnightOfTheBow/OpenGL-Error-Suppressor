# OpenGL Error Suppressor
A Minecraft Forge mod that lets only unique OpenGL errors log exactly once, suppressing all subsequent spam.

_Supports all versions from 1.16.5 up to 26.2._ (Tried and tested.)

You may notice a few warning logs in your console during startup. These are completely harmless and can be safely ignored as the mod works regardless.
<details><summary>Example Warning Logs (Safe to Ignore)</summary>

```text
[xx:xx:xx] [main/WARN] [mixin/]: Error loading class: com/mojang/blaze3d/platform/Window (java.lang.ClassNotFoundException: null)
[xx:xx:xx] [main/WARN]: Error loading class: net/minecraft/client/MainWindow (java.lang.ClassNotFoundException: net/minecraft/client/MainWindow)
[xx:xx:xx] [main/WARN]: @Mixin target net/minecraft/client/MainWindow was not found opengl_error_suppressor.mixins.json:WindowMixin from mod opengl_error_suppressor
```
</details>

## License & Attribution
This mod is licensed under the [MIT License](LICENSE).

This project is adapted from [Shut Up GL Error](https://github.com/JamCoreModding/shut-up-gl-error) (Copyright © 2025 Jamalam).