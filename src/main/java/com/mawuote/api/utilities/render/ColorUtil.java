package com.mawuote.api.utilities.render;

import java.awt.image.*;
import java.awt.*;

public class ColorUtil
{
    public static Color averageColor(final BufferedImage bi, final int width, final int height, final int pixelStep) {
        final int[] color = new int[3];
        for (int x = 0; x < width; x += pixelStep) {
            for (int y = 0; y < height; y += pixelStep) {
                final Color pixel = new Color(bi.getRGB(x, y));
                final int[] array = color;
                final int n = 0;
                array[n] += pixel.getRed();
                final int[] array2 = color;
                final int n2 = 1;
                array2[n2] += pixel.getGreen();
                final int[] array3 = color;
                final int n3 = 2;
                array3[n3] += pixel.getBlue();
            }
        }
        final int num = width * height;
        return new Color(color[0] / num, color[1] / num, color[2] / num);
    }
}
