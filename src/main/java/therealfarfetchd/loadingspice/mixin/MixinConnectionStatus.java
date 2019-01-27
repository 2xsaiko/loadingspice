package therealfarfetchd.loadingspice.mixin;

import net.minecraft.client.gui.CloseWorldGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ingame.ChatGui;
import net.minecraft.client.gui.menu.DownloadingTerrainGui;
import net.minecraft.client.gui.menu.ServerConnectingGui;
import net.minecraft.client.gui.menu.WorkingGui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WorkingGui.class, CloseWorldGui.class, DownloadingTerrainGui.class, ServerConnectingGui.class, ChatGui.class})
public class MixinConnectionStatus extends Gui {

    @Inject(method = "draw(IIF)V", at = @At("RETURN"))
    private void draw(int mouseX, int mouseY, float delta, CallbackInfo ci) {
    }

}
