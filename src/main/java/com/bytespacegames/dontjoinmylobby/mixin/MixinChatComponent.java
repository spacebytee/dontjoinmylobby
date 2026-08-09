package com.bytespacegames.dontjoinmylobby.mixin;

import com.bytespacegames.dontjoinmylobby.DontJoinMyLobby;
import com.bytespacegames.dontjoinmylobby.RegexManager;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class MixinChatComponent {
    @Inject(at = @At("HEAD"), method = "addMessage", cancellable = true)
    public void addMessage(Component component, CallbackInfo ci) {
        if (!DontJoinMyLobby.INSTANCE.isEnabled()) return;
        if (DontJoinMyLobby.INSTANCE.isHypixelOnly() && !DontJoinMyLobby.INSTANCE.isOnHypixel()) return;

        String contents = component.getString().trim();
        contents = contents.replaceAll("§.", "");

        boolean matches = false;
        for (String pattern : RegexManager.INSTANCE.getPatterns()) {
            if (contents.matches(pattern)) {
                matches = true;
                break;
            }
        }
        if (!matches) return;
        ci.cancel();
    }
}