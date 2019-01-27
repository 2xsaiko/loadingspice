package therealfarfetchd.loadingspice.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import therealfarfetchd.loadingspice.gui.ConnectionStatusRenderer;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    /**
     * Fixes a crash where the screen gets resized after GameRenderer was created but before WorldRenderer was created
     */
    @Redirect(method = "method_3169(II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;method_3242(II)V"))
    private void method_3242(WorldRenderer worldRenderer, int int_1, int int_2) {
        if (worldRenderer != null) {
            worldRenderer.method_3242(int_1, int_2);
        }
    }

    @Inject(method = "method_3192(FJZ)V", at = @At("RETURN"))
    private void method_3192(float float_1, long long_1, boolean boolean_1, CallbackInfo ci) {
        ConnectionStatusRenderer.draw();
    }

}
