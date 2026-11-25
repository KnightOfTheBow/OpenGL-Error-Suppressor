/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

package archer.openglerrorcreator.mixin;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("TAIL"), require = 1)
    private void runTick(boolean advanceGameTime, CallbackInfo info) {
        GL11.glEnable(-1);
    }
}