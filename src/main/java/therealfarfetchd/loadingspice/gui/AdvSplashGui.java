package therealfarfetchd.loadingspice.gui;

import net.minecraft.client.font.FontRenderer;
import net.minecraft.client.gl.GlFramebuffer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.platform.GlStateManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import therealfarfetchd.loadingspice.LoadingProgressImpl;
import therealfarfetchd.loadingspice.LoadingSpiceConfig;
import therealfarfetchd.loadingspice.api.LoadingProgress.TaskInfo;

public class AdvSplashGui extends Gui {

    private static final Logger logger = LogManager.getLogger();

    private static final Identifier LOGO = new Identifier("textures/gui/title/mojang.png");

    private Identifier logo;

    private Identifier icon;

    private int prog = 0;
    private long lastDraw;

    private TaskInfo[] tasks = new TaskInfo[5];

    protected void onInitialized() {
        try {
            InputStream in = this.client.getResourcePackDownloader().getPack().open(ResourceType.ASSETS, LOGO);
            this.logo = this.client.getTextureManager().registerDynamicTexture("logo", new NativeImageBackedTexture(NativeImage.fromInputStream(in)));
        } catch (IOException var2) {
            logger.error("Unable to load logo: {}", LOGO, var2);
        }

        try {
            final LoadingSpiceConfig cfg = LoadingSpiceConfig.INSTANCE;
            final Identifier tex = cfg.splashIconTexture;

            InputStream in;

            if (cfg.useExtTextureDir) {
                in = new FileInputStream(new File(cfg.extTextureDir, String.format("%s/%s", tex.getNamespace(), tex.getPath())));
            } else {
                in = this.client.getResourcePackDownloader().getPack().open(ResourceType.ASSETS, tex);
            }

            this.icon = this.client.getTextureManager().registerDynamicTexture("loading_icon", new NativeImageBackedTexture(NativeImage.fromInputStream(in)));
        } catch (IOException var2) {
            logger.error("Unable to load logo: {}", LOGO, var2);
        }
    }

    public void onClosed() {
        this.client.getTextureManager().destroyTexture(this.logo);
        this.client.getTextureManager().destroyTexture(this.icon);
        this.logo = null;
        this.icon = null;
    }

    public void draw(int mouseX, int mouseY, float delta) {
        GlFramebuffer fb = client.getFramebuffer();

        fb.beginWrite(true);

        drawFB();

        fb.endWrite();
        fb.draw(this.client.window.getWidth(), this.client.window.getHeight());

        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1F);
    }

    private void drawFB() {
        drawLogo();
        drawLoadCircle();
        drawProgress();
    }

    private void drawProgress() {
        int color = LoadingSpiceConfig.INSTANCE.splashTextColor.getRGB();

        final FontRenderer fr = client.fontRenderer;
        if (fr == null) return;

        int count = 0;
        tasks[0] = LoadingProgressImpl.INSTANCE.getCurrentTask();

        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) break;

            count++;

            if (i != tasks.length - 1) {
                tasks[i + 1] = tasks[i].getParent();
            }
        }

        fr.draw("Loading...", 2, height - fr.fontHeight - 2, color);

        for (int i = 0; i < count; i++) {
            TaskInfo task = tasks[i];

            fr.draw(task.getText(), 2, height - (fr.fontHeight + 1) * (count - i + 1) - 1, color);
        }

    }

    private void drawLogo() {
        this.client.getTextureManager().bindTexture(this.logo);

        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepthTest();
        GlStateManager.enableTexture();

        Tessellator tessellator_1 = Tessellator.getInstance();
        BufferBuilder bufferBuilder_1 = tessellator_1.getBufferBuilder();
        bufferBuilder_1.begin(7, VertexFormats.POSITION_UV_COLOR);
        bufferBuilder_1.vertex(0.0D, (double) this.client.window.getHeight(), 0.0D).texture(0.0D, 0.0D).color(255, 255, 255, 255).next();
        bufferBuilder_1.vertex((double) this.client.window.getWidth(), (double) this.client.window.getHeight(), 0.0D).texture(0.0D, 0.0D).color(255, 255, 255, 255).next();
        bufferBuilder_1.vertex((double) this.client.window.getWidth(), 0.0D, 0.0D).texture(0.0D, 0.0D).color(255, 255, 255, 255).next();
        bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).texture(0.0D, 0.0D).color(255, 255, 255, 255).next();
        tessellator_1.draw();

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.client.method_1501((this.client.window.getScaledWidth() - 256) / 2, (this.client.window.getScaledHeight() - 256) / 2, 0, 0, 256, 256, 255, 255, 255, 255);
    }

    private void drawLoadCircle() {
        LoadingSpiceConfig cfg = LoadingSpiceConfig.INSTANCE;

        LoadingIconRenderer.draw(width, height, prog, cfg.splashIconAlign, cfg.splashIconSize, cfg.splashIconGrid, cfg.splashIconColor, icon);
        if (System.currentTimeMillis() - lastDraw > cfg.splashIconAnimSpeed) {
            lastDraw = System.currentTimeMillis();
            prog++;
        }
    }

}
