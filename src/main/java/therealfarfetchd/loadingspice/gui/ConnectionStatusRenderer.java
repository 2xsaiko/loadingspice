package therealfarfetchd.loadingspice.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.menu.DownloadingTerrainGui;
import net.minecraft.client.gui.menu.ServerConnectingGui;
import net.minecraft.client.gui.menu.WorkingGui;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.util.function.LongConsumer;

import therealfarfetchd.loadingspice.LoadingSpice;
import therealfarfetchd.loadingspice.LoadingSpiceConfig;
import therealfarfetchd.loadingspice.NetTracker;

import static com.mojang.blaze3d.platform.GlStateManager.color4f;
import static com.mojang.blaze3d.platform.GlStateManager.disableLighting;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class ConnectionStatusRenderer {

    private static final Identifier TEXTURE = new Identifier(LoadingSpice.MODID, "textures/sploadicons.png");

    private static long rxProg = 0;
    private static long txProg = 0;

    public static void draw() {
        if (!checkShouldRender()) return;
        MinecraftClient client = MinecraftClient.getInstance();

        int rx = NetTracker.rx.get();
        int tx = NetTracker.tx.get();

        boolean rxInput = false;
        boolean txInput = false;

        if (rx > 1) {
            NetTracker.rx.addAndGet(-rx);
            rxInput = true;
        }

        if (tx > 1) {
            NetTracker.tx.addAndGet(-tx);
            txInput = true;
        }

        disableLighting();
        color4f(1, 1, 1, 1);

        client.getTextureManager().bindTexture(TEXTURE);

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBufferBuilder();

        buf.begin(GL_QUADS, VertexFormats.POSITION_UV);

        if (!getTickState(rxInput, rxProg, $ -> rxProg = $)) {
            buf.vertex(2, 9, 0).texture(20 / 64f, (19 + 7) / 64f).next();
            buf.vertex(9, 9, 0).texture(27 / 64f, (19 + 7) / 64f).next();
            buf.vertex(9, 2, 0).texture(27 / 64f, (12 + 7) / 64f).next();
            buf.vertex(2, 2, 0).texture(20 / 64f, (12 + 7) / 64f).next();
        } else {
            buf.vertex(2, 9, 0).texture(20 / 64f, 19 / 64f).next();
            buf.vertex(9, 9, 0).texture(27 / 64f, 19 / 64f).next();
            buf.vertex(9, 2, 0).texture(27 / 64f, 12 / 64f).next();
            buf.vertex(2, 2, 0).texture(20 / 64f, 12 / 64f).next();
        }

        if (!getTickState(txInput, txProg, $ -> txProg = $)) {
            buf.vertex(2 + 8, 9, 0).texture((20 + 7) / 64f, (19 + 7) / 64f).next();
            buf.vertex(9 + 8, 9, 0).texture((27 + 7) / 64f, (19 + 7) / 64f).next();
            buf.vertex(9 + 8, 2, 0).texture((27 + 7) / 64f, (12 + 7) / 64f).next();
            buf.vertex(2 + 8, 2, 0).texture((20 + 7) / 64f, (12 + 7) / 64f).next();
        } else {
            buf.vertex(2 + 8, 9, 0).texture((20 + 7) / 64f, 19 / 64f).next();
            buf.vertex(9 + 8, 9, 0).texture((27 + 7) / 64f, 19 / 64f).next();
            buf.vertex(9 + 8, 2, 0).texture((27 + 7) / 64f, 12 / 64f).next();
            buf.vertex(2 + 8, 2, 0).texture((20 + 7) / 64f, 12 / 64f).next();
        }

        buf.vertex(2, 18, 0).texture(20 / 64f, 34 / 64f).next();
        buf.vertex(9, 18, 0).texture(27 / 64f, 34 / 64f).next();
        buf.vertex(9, 10, 0).texture(27 / 64f, 26 / 64f).next();
        buf.vertex(2, 10, 0).texture(20 / 64f, 26 / 64f).next();

        buf.vertex(2 + 8, 18, 0).texture((20 + 7) / 64f, 34 / 64f).next();
        buf.vertex(9 + 8, 18, 0).texture((27 + 7) / 64f, 34 / 64f).next();
        buf.vertex(9 + 8, 10, 0).texture((27 + 7) / 64f, 26 / 64f).next();
        buf.vertex(2 + 8, 10, 0).texture((20 + 7) / 64f, 26 / 64f).next();

        t.draw();
    }

    private static boolean getTickState(boolean input, long enableTime, LongConsumer setter) {
        final long time = System.currentTimeMillis();
        if (input && enableTime == 0) {
            setter.accept(enableTime = time);
        }

        boolean output = enableTime != 0 && (time - enableTime) / 50 % 2 != 0;

        if (!input && output && (time - enableTime) / 50 > 2) {
            setter.accept(0);
            output = false;
        }

        return output;
    }

    private static boolean checkShouldRender() {
        return isInGame() && checkMode();
    }

    private static boolean isInGame() {
        MinecraftClient client = MinecraftClient.getInstance();

        return client.getNetworkHandler() != null || client.currentGui instanceof ServerConnectingGui;
    }

    private static boolean checkMode() {
        MinecraftClient client = MinecraftClient.getInstance();
        int mode = LoadingSpiceConfig.INSTANCE.netIndicatorMode;

        if (mode >= 1 && (client.currentGui instanceof WorkingGui ||
            client.currentGui instanceof DownloadingTerrainGui ||
            client.currentGui instanceof ServerConnectingGui)) return true;

        if (mode >= 2 && client.options.keyPlayerList.isPressed()) return true;

        if (mode >= 3) return true;

        return false;
    }

}
