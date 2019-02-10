package therealfarfetchd.loadingspice.mixin.progress;

import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import therealfarfetchd.loadingspice.LoadingProgressImpl;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;<init>(Lnet/minecraft/client/texture/SpriteAtlasTexture;)V"))
    private void onModelLoadStart(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.pushTask().withTaskName("Loading models");
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 6, shift = Shift.AFTER))
    private void onModelLoadEnd(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.popTask();
    }

}
