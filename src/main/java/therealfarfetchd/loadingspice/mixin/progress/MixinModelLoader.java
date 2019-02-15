package therealfarfetchd.loadingspice.mixin.progress;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotationContainer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import therealfarfetchd.loadingspice.LoadingProgressImpl;

@Mixin(ModelLoader.class)
public abstract class MixinModelLoader {

//    @Inject(method = "addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", at = @At("HEAD"))
//    private void onModelAddStart(ModelIdentifier modelIdentifier_1, CallbackInfo ci) {
//        LoadingProgressImpl.INSTANCE.pushTask().withTaskName(String.format("Adding model '%s'", modelIdentifier_1.toString()));
//    }
//
//    @Inject(method = "addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", at = @At("RETURN"))
//    private void onModelAddEnd(ModelIdentifier modelIdentifier_1, CallbackInfo ci) {
//        LoadingProgressImpl.INSTANCE.popTask();
//    }
//
//    @Inject(method = "bake(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/render/model/ModelRotationContainer;)Lnet/minecraft/client/render/model/BakedModel;", at = @At("HEAD"))
//    private void onModelBakeStart(Identifier identifier_1, ModelRotationContainer modelRotationContainer_1, CallbackInfoReturnable<BakedModel> cir) {
//        LoadingProgressImpl.INSTANCE.pushTask().withTaskName(String.format("Baking model '%s'", identifier_1.toString()));
//    }
//
//    @Inject(method = "bake(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/render/model/ModelRotationContainer;)Lnet/minecraft/client/render/model/BakedModel;", at = @At("RETURN"))
//    private void onModelBakeEnd(Identifier identifier_1, ModelRotationContainer modelRotationContainer_1, CallbackInfoReturnable<BakedModel> cir) {
//        LoadingProgressImpl.INSTANCE.popTask();
//    }

}
