package therealfarfetchd.loadingspice.gui;

import net.minecraft.client.gui.WorldGenerationProgressScreen;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.platform.GlStateManager;

import therealfarfetchd.loadingspice.LoadingSpice;
import therealfarfetchd.loadingspice.LoadingSpiceConfig;

import static com.mojang.blaze3d.platform.GlStateManager.popMatrix;
import static com.mojang.blaze3d.platform.GlStateManager.pushMatrix;
import static com.mojang.blaze3d.platform.GlStateManager.rotatef;
import static java.lang.Math.min;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class SPLevelLoadScreen extends WorldGenerationProgressScreen {

    private static final Identifier TEXTURE = new Identifier(LoadingSpice.MODID, "textures/sploadicons.png");

    private final WorldGenerationProgressTracker tracker;

    public SPLevelLoadScreen(WorldGenerationProgressTracker tracker) {
        super(tracker);
        this.tracker = tracker;
    }

    @Override
    public void draw(int mouseX, int mouseY, float delta) {
        this.drawBackground();

        GlStateManager.enableBlend();

        int genChunksPct = min(tracker.getProgressPercentage(), 100);

        int color = LoadingSpiceConfig.INSTANCE.loadingIconColor.getRGB();

        // if (LoadingSpiceConfig.INSTANCE.verboseLoad) {
        //     client.fontRenderer.draw("Total tasks: " + totalTasks, 26, height - 55 - 26 + 2, color);
        //     client.fontRenderer.draw("Queued tasks: " + queuedTasks, 26, height - 55 - 26 + 10, color);
        //     client.fontRenderer.draw("Running tasks: " + runningTasks, 26, height - 55 - 26 + 18, color);
        //     client.fontRenderer.draw("Generated chunks: " + genChunks, 26, height - 55 - 26 + 26, color);
        // }

        // drawProgressBar(2, height - 55 - 26, taskPct / 100f);
        // drawProgressBar(14, height - 55 - 26, genChunksPct / 100f);
        drawProgressBar(2, height - 55 - 26, genChunksPct / 100f);

        // drawIcon(3, height - 55 - 26 - 13, 0);
        // drawIcon(15, height - 55 - 26 - 13, 1);
        drawIcon(3, height - 55 - 26 - 13, 1);

        pushMatrix();
        rotatef(-90, 0, 0, 1);
        // final String s1 = taskPct + "%";
        final String s2 = genChunksPct + "%";
        // client.fontRenderer.draw(s1, -height + 55 + 26 - 52 - client.fontRenderer.getStringWidth(s1), 4, color);
        // client.fontRenderer.draw(s2, -height + 55 + 26 - 52 - client.fontRenderer.getStringWidth(s2), 16, color);
        client.fontRenderer.draw(s2, -height + 55 + 26 - 52 - client.fontRenderer.getStringWidth(s2), 4, color);
        popMatrix();

        int scale = 2;
        int size = scale * tracker.getSize();
        drawChunkMap(this.tracker, 16 + size / 2, height - size / 2 - 4, scale, 0);
    }

    private void drawIcon(int x, int y, int index) {
        int iconWidth = 8;
        int iconHeight = 11;

        client.getTextureManager().bindTexture(TEXTURE);
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBufferBuilder();
        buf.begin(GL_QUADS, VertexFormats.POSITION_UV);
        buf.vertex(x, y + iconHeight, 0).texture((index * iconWidth) / 64f, iconHeight / 64f).next();
        buf.vertex(x + iconWidth, y + iconHeight, 0).texture(((index + 1) * iconWidth) / 64f, iconHeight / 64f).next();
        buf.vertex(x + iconWidth, y, 0).texture(((index + 1) * iconWidth) / 64f, 0 / 64f).next();
        buf.vertex(x, y, 0).texture((index * iconWidth) / 64f, 0 / 64f).next();
        t.draw();
    }

    private void drawProgressBar(int x, int y, float progress) {
        int split = (int) (progress * 52);

        client.getTextureManager().bindTexture(TEXTURE);
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBufferBuilder();
        buf.begin(GL_QUADS, VertexFormats.POSITION_UV);
        buf.vertex(x, y + 52, 0).texture(0 / 64f, 64 / 64f).next();
        buf.vertex(x + 10, y + 52, 0).texture(10 / 64f, 64 / 64f).next();
        buf.vertex(x + 10, y, 0).texture(10 / 64f, 12 / 64f).next();
        buf.vertex(x, y, 0).texture(0 / 64f, 12 / 64f).next();

        buf.vertex(x, y + 52, 0).texture(10 / 64f, 64 / 64f).next();
        buf.vertex(x + 10, y + 52, 0).texture(20 / 64f, 64 / 64f).next();
        buf.vertex(x + 10, y + 52 - split, 0).texture(20 / 64f, (64 - split) / 64f).next();
        buf.vertex(x, y + 52 - split, 0).texture(10 / 64f, (64 - split) / 64f).next();
        t.draw();
    }

}
