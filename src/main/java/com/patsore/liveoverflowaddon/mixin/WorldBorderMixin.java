package com.patsore.liveoverflowaddon.mixin;

import net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldBorderInitializeS2CPacket.class)
public class WorldBorderMixin {
    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci){
        ci.cancel();
    }
}
