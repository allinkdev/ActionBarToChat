package me.allinkdev.actionbartochat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ClientPlayerEntityMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    public void setOverlayMessage(Text message, boolean tinted, CallbackInfo ci) {
        if (message.getContent() instanceof TranslatableTextContent) {
            return;
        }

        ci.cancel();

        if (client.player == null) {
            return;
        }

        client.player.sendMessage(message, false);
    }
}
