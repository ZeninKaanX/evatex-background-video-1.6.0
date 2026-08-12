package com.videomenu.mixin;

import com.videomenu.VideoSelectScreen;
import com.videomenu.video.VideoBackground;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_442;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {class_442.class})
public abstract class TitleScreenMixin extends class_437 {
    protected TitleScreenMixin() {
        super(class_2561.method_43473());
    }

    @Inject(method = {"method_25426"}, at = {@At(value = "TAIL")})
    private void videomenu$addVideoMenuButton(CallbackInfo ci) {
        this.method_37063(class_4185.method_46430(class_2561.method_43470("Video Menüsü"), button -> this.field_22787.method_1507(new VideoSelectScreen(this, 0))).method_46434(this.field_22789 - 104, 4, 100, 20).method_46431());
    }

    @Inject(method = {"method_25426"}, at = {@At(value = "HEAD")})
    private void videomenu$resumeVideo(CallbackInfo ci) {
        VideoBackground.INSTANCE.resume();
    }

    @Inject(method = {"method_25432"}, at = {@At(value = "HEAD")})
    private void videomenu$pauseVideo(CallbackInfo ci) {
        VideoBackground.INSTANCE.pause();
    }

    @Inject(method = {"method_25394(Lnet/minecraft/class_332;IIF)V"}, at = {@At(value = "TAIL")})
    private void videomenu$renderVideo(class_332 context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        VideoBackground.INSTANCE.render(context, this.field_22789, this.field_22790);
    }
}
