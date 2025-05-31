package com.bytespacegames.dontjoinmylobby.mixin;

import com.bytespacegames.dontjoinmylobby.DontJoinMyLobby;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class MixinChatComponent {
	/*@Inject(at = @At("HEAD"), method = "doWorldLoad")
	void init(LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, boolean bl, CallbackInfo ci) {
		System.out.println("mixin");
	}*/
	/*@Inject(at = @At("HEAD"), method = "destroy", cancellable = true)
	void destroy(CallbackInfo ci) {
		System.out.println("mixin");
		ci.cancel();
	}*/
    private final String[] TRIGGERS = { "joined the lobby!", "slid into the lobby", "spooked into the lobby"};
    @Inject(at = @At("HEAD"), method = "addMessage", cancellable = true)
    public void addMessage(Component component, CallbackInfo ci) {
        if (!DontJoinMyLobby.INSTANCE.isEnabled()) return;
        if (DontJoinMyLobby.INSTANCE.isHypixelOnly() && !DontJoinMyLobby.INSTANCE.isOnHypixel()) return;
        String contents = component.getString().trim();
        contents = contents.replaceAll("§.", "");

        if (contents.contains(":")) return;
        if (!contents.contains("[MVP+")) return;
        System.out.println(contents);
        if (!contents.startsWith("[MVP+") && !contents.startsWith(">>>")) return;

        boolean containsTrigger = false;
        for (String s : TRIGGERS) {
            if (contents.contains(s)) {
                containsTrigger = true;
                break;
            }
        }
        if (!containsTrigger) return;
        ci.cancel();
    }
}