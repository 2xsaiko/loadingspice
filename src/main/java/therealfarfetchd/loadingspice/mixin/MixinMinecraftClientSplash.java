package therealfarfetchd.loadingspice.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import therealfarfetchd.loadingspice.LoadingSpiceConfig;
import therealfarfetchd.loadingspice.SplashUtilsImpl;
import therealfarfetchd.loadingspice.gui.AdvSplashGui;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClientSplash {

    @Inject(
        method = "init()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;method_1567()V",
            shift = Shift.AFTER
        )
    )
    private void startSplash(CallbackInfo ci) {
        SplashUtilsImpl.INSTANCE.start((MinecraftClient) (Object) this);
    }

    @Inject(
        method = "init()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;<init>(Lnet/minecraft/client/MinecraftClient;)V",
            shift = Shift.AFTER
        )
    )
    private void stopSplash(CallbackInfo ci) {
        SplashUtilsImpl.INSTANCE.finish();
    }

    @Inject(
        method = "init()V",
        at = {
            // @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 3, shift = Shift.BEFORE),
            @At(value = "CONSTANT", args = "stringValue=Startup", shift = Shift.BEFORE),
            // @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/SpriteAtlasTexture;<init>(Ljava/lang/String;)V"),
            // @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 8, shift = Shift.BEFORE),
            @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;<init>(Lnet/minecraft/client/MinecraftClient;)V"),
        }
    )
    private void pauseSplash(CallbackInfo ci) {
        SplashUtilsImpl.INSTANCE.pause();
    }

    @Inject(
        method = "init()V",
        at = {
            // @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 3, shift = Shift.AFTER),
            @At(value = "CONSTANT", args = "stringValue=Post startup", shift = Shift.AFTER),
            // @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;<init>(Lnet/minecraft/client/texture/SpriteAtlasTexture;)V"),
            // @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 8, shift = Shift.AFTER),
            @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 10, shift = Shift.AFTER),
        }
    )
    private void resumeSplash(CallbackInfo ci) {
        SplashUtilsImpl.INSTANCE.resume();
    }

    @Redirect(
        method = "init()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openGui(Lnet/minecraft/client/gui/Gui;)V", ordinal = 0)
    )
    private void openGui(MinecraftClient client, Gui gui) {
        if (LoadingSpiceConfig.INSTANCE.newSplash) {
            client.openGui(new AdvSplashGui());
        } else {
            client.openGui(gui);
        }
    }

}
