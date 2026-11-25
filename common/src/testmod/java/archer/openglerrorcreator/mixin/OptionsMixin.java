package archer.openglerrorcreator.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.PreferredGraphicsApi;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Options.class, remap = false)
public class OptionsMixin {
    @Final
    @Shadow
    private OptionInstance<PreferredGraphicsApi> preferredGraphicsBackend;

    @Shadow
    private PreferredGraphicsApi preferredGraphicsBackendFromStartup;

    @Inject(method = "load()V", at = @At("TAIL"), require = 1)
    public void load(CallbackInfo info) {
        preferredGraphicsBackend.set(PreferredGraphicsApi.OPENGL);
        preferredGraphicsBackendFromStartup = PreferredGraphicsApi.OPENGL;
    }

    // Reverts any change away from the Vulkan backend
    @Inject(method = "preferredGraphicsBackend", at = @At("HEAD"), require = 1)
    private void preferredGraphicsBackend(CallbackInfoReturnable<OptionInstance<PreferredGraphicsApi>> info) {
        if (preferredGraphicsBackend.get() != PreferredGraphicsApi.OPENGL) preferredGraphicsBackend.set(PreferredGraphicsApi.OPENGL);
    }
}
