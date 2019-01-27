package therealfarfetchd.loadingspice.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.WorldGenerationProgressGui;
import net.minecraft.client.gui.WorldGenerationProgressTracker;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import therealfarfetchd.loadingspice.gui.SPLevelLoadGui;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Redirect(
        method = "startIntegratedServer(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/world/level/LevelInfo;)V",
        at = @At(
            value = "NEW",
            target = "net/minecraft/client/gui/WorldGenerationProgressGui"
        )
    )
    public WorldGenerationProgressGui constructWorkingGui(WorldGenerationProgressTracker tracker) {
        return new SPLevelLoadGui(tracker);
    }

}
