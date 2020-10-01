package com.impact.System;


import gloomyfolken.hooklib.asm.Hook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Hooks {

    @Hook
    public static void loadScreen(Minecraft mc) throws LWJGLException {
        mc.renderEngine.bindTexture(new ResourceLocation("impact", "textures/gui/title/title.png"));
    }

    @Hook
    public static void createDisplay(ForgeHooksClient clazz) {
        Display.setTitle("Impact");
        ResourceLocation icon = new ResourceLocation("impact", "textures/gui/title/fav.png");

        try {
            InputStream inputstream = Config.class.getResourceAsStream("/assets/" + icon.getResourceDomain() + "/" + icon.getResourcePath());
            Display.setIcon(new ByteBuffer[]{call(inputstream)});
        } catch (IOException ignore) {
        }
    }

    private static ByteBuffer call(InputStream is) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(is);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[]) null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        int[] aint1 = aint;
        int i = aint.length;

        for (int j = 0; j < i; ++j) {
            int k = aint1[j];
            bytebuffer.putInt(k << 8 | k >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }


}
