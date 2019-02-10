package therealfarfetchd.loadingspice.mixin;

import net.minecraft.client.gui.CloseWorldScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.menu.DownloadingTerrainScreen;
import net.minecraft.client.gui.menu.ServerConnectingScreen;
import net.minecraft.client.gui.menu.WorkingScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import therealfarfetchd.loadingspice.LoadingSpiceConfig;
import therealfarfetchd.loadingspice.gui.LoadingIconRenderer;
import therealfarfetchd.loadingspice.gui.SPLevelLoadScreen;

@Mixin({WorkingScreen.class, CloseWorldScreen.class, DownloadingTerrainScreen.class, ServerConnectingScreen.class, SPLevelLoadScreen.class})
public abstract class MixinGuiLoadingCircle extends Screen {

    private int prog = 0;
    private long lastDraw;

    @Inject(method = "draw(IIF)V", at = @At("RETURN"))
    private void draw(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        LoadingSpiceConfig cfg = LoadingSpiceConfig.INSTANCE;

        LoadingIconRenderer.draw(width, height, prog, cfg.loadingIconAlign, cfg.loadingIconSize, cfg.loadingIconGrid, cfg.loadingIconColor, cfg.loadingIconTexture);
        if (System.currentTimeMillis() - lastDraw > cfg.loadingIconAnimSpeed) {
            lastDraw = System.currentTimeMillis();
            prog++;
        }
    }

}
