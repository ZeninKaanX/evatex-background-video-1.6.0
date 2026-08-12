package com.videomenu.mixin;

import com.videomenu.video.VideoBackground;
import net.minecraft.class_332;
import net.minecraft.class_766;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {class_766.class})
public abstract class RotatingCubeMapRendererMixin {
    @Inject(method = {"method_3317(Lnet/minecraft/class_332;IIZ)V"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void videomenu$renderVideo(class_332 context, int width, int height, boolean rotate, CallbackInfo ci) {
        if (VideoBackground.INSTANCE.render(context, width, height)) {
            ci.cancel();
        }
    }
}
