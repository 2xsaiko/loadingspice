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

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/SpriteAtlasTexture;<init>(Ljava/lang/String;)V"))
    void onSpriteAtlasStart(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.pushTask().withTaskName("Creating sprite atlas");
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V", shift = Shift.AFTER))
    void onSpriteAtlasEnd(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.popTask();
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;<init>(Lnet/minecraft/client/texture/SpriteAtlasTexture;)V"))
    void onModelLoadStart(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.pushTask().withTaskName("Loading models");
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;addListener(Lnet/minecraft/resource/ResourceReloadListener;)V", ordinal = 6, shift = Shift.AFTER))
    void onModelLoadEnd(CallbackInfo ci) {
        LoadingProgressImpl.INSTANCE.popTask();
    }

}
