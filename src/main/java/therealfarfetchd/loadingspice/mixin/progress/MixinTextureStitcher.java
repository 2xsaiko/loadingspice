package therealfarfetchd.loadingspice.mixin.progress;

import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.client.texture.TextureStitcher.Holder;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import therealfarfetchd.loadingspice.LoadingProgressImpl;

@Mixin(TextureStitcher.class)
public class MixinTextureStitcher {

    @Inject(method = "tryFit(Lnet/minecraft/client/texture/TextureStitcher$Holder;)Z", at = @At("HEAD"))
    private void startStitchSprite(Holder textureStitcher$Holder_1, CallbackInfoReturnable<Boolean> cir) {
        LoadingProgressImpl.INSTANCE.pushTask().withTaskName(String.format("Stitching sprite '%s'", textureStitcher$Holder_1.getSprite().getId()));
    }

    @Inject(method = "tryFit(Lnet/minecraft/client/texture/TextureStitcher$Holder;)Z", at = @At("RETURN"))
    private void endStitchSprite(Holder textureStitcher$Holder_1, CallbackInfoReturnable<Boolean> cir) {
        LoadingProgressImpl.INSTANCE.popTask();
    }

}
