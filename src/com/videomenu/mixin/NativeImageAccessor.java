package com.videomenu.mixin;

import net.minecraft.class_1011;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {class_1011.class})
public interface NativeImageAccessor {
    @Accessor(value = "field_4988")
    public long videomenu$getPointer();
}
