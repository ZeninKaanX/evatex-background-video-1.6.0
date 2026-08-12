package com.videomenu.mixin;

import com.videomenu.video.VideoBackground;
import net.minecraft.class_766;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {class_766.class})
public abstract class RotatingCubeMapRendererMixin {
    @Inject(method = {"method_3317"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void videomenu$skipPanorama(float tickDelta, float rotation, CallbackInfo ci) {
        if (VideoBackground.INSTANCE.isActive()) {
            ci.cancel();
        }
    }
}
