package therealfarfetchd.loadingspice.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.platform.GlStateManager.DstBlendFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcBlendFactor;

import java.awt.Color;

import static com.mojang.blaze3d.platform.GlStateManager.blendFunc;
import static com.mojang.blaze3d.platform.GlStateManager.color4f;
import static com.mojang.blaze3d.platform.GlStateManager.disableBlend;
import static com.mojang.blaze3d.platform.GlStateManager.enableBlend;
import static com.mojang.blaze3d.platform.GlStateManager.popMatrix;
import static com.mojang.blaze3d.platform.GlStateManager.pushMatrix;
import static com.mojang.blaze3d.platform.GlStateManager.translatef;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class LoadingIconRenderer {

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public static void draw(int width, int height, int prog, int[] iconAlign, int[] iconSize, int[] iconGrid, Color iconColor, Identifier iconTexture) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(iconTexture);

        int posX = iconAlign[0] < 0 ? width - iconSize[0] + iconAlign[0] : iconAlign[0];
        int posY = iconAlign[1] < 0 ? height - iconSize[1] + iconAlign[1] : iconAlign[1];

        color4f(iconColor.getRed() / 255f, iconColor.getGreen() / 255f, iconColor.getBlue() / 255f, iconColor.getAlpha() / 255f);

        float uvX = (prog % iconGrid[0]) / (float) iconGrid[0];
        float uvY = (prog / iconGrid[0]) / (float) iconGrid[1];
        float uvX1 = ((prog % iconGrid[0]) + 1) / (float) iconGrid[0];
        float uvY1 = ((prog / iconGrid[0]) + 1) / (float) iconGrid[1];

        pushMatrix();
        enableBlend();
        blendFunc(SrcBlendFactor.SRC_ALPHA, DstBlendFactor.ONE_MINUS_SRC_ALPHA);
        translatef(posX, posY, 0);

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBufferBuilder();
        buf.begin(GL_QUADS, VertexFormats.POSITION_UV);
        buf.vertex(0, iconSize[1], 0).texture(uvX, uvY1).next();
        buf.vertex(iconSize[0], iconSize[1], 0).texture(uvX1, uvY1).next();
        buf.vertex(iconSize[0], 0, 0).texture(uvX1, uvY).next();
        buf.vertex(0, 0, 0).texture(uvX, uvY).next();
        t.draw();

        disableBlend();
        popMatrix();
    }

}
