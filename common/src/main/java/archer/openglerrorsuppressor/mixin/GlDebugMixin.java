/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

package archer.openglerrorsuppressor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.renderpearl.backend.opengl.GlDebug;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GlDebug.class, remap = false)
public class GlDebugMixin {
    @Unique
    private static final ObjectSet<String> openglerrorsuppressor$LOGGED_GL_ERRORS = ObjectSets.synchronize(new ObjectOpenHashSet<>());

    @Inject(method = "printDebugLog(IIIIIJJ)V", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"), cancellable = true, require = 1)
    private void printDebugLog(int source, int type, int id, int severity, int length, long message, long userParam, CallbackInfo info, @Local(ordinal = 0) String msg) {
        if (!openglerrorsuppressor$LOGGED_GL_ERRORS.add(msg)) info.cancel();
    }
}