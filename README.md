# OpenGL Error Suppressor
A Minecraft Fabric mod that lets only unique OpenGL errors log exactly once, suppressing all subsequent spam.

_Supports all versions from 1.14.4 up to 26.2._ (Tried and tested.)

You may notice a few warning logs in your console during startup. These are completely harmless and can be safely ignored as the mod works regardless.
<details><summary>Example Warning Logs (Safe to Ignore)</summary>

```text
[xx:xx:xx] [main/WARN]: Error loading class: net/minecraft/class_1041 (java.lang.ClassNotFoundException: net/minecraft/class_1041)
[xx:xx:xx] [main/WARN]: @Mixin target net/minecraft/class_1041 was not found opengl-error-suppressor.mixins.json:WindowMixin from mod opengl-error-suppressor
[xx:xx:xx] [main/WARN]: @Mixin target net/minecraft/class_1041 was not found opengl-error-suppressor.mixins.json:WindowMixin from mod opengl-error-suppressor
```

</details>

## License & Attribution
This mod is licensed under the MIT license.

This project is adapted from [Shut Up GL Error](https://github.com/JamCoreModding/shut-up-gl-error) (Copyright © 2025 Jamalam).