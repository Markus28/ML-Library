import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MNISTImage {

    private final double[] pixels;
    private final int width = 28;
    private final int height = 28;

    public MNISTImage(double[] values)
    {
        pixels = values;
    }

    public void writeImage(String path)
    {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f;
        int row, column;
        int r, g, b;
        int a = 255;
        int p;

        for(int i = 0; i < pixels.length; i++)
        {
            row = i/width;
            column = i%width;
            r = g= b = (int) (255.0*(1.0-pixels[i]));
            p = (a<<24) | (r<<16) | (g<<8) | b;

            img.setRGB(column, row, p);
        }

        try
        {
            f = new File(path);
            ImageIO.write(img, "png", f);
        }

        catch (IOException e)
        {
            System.err.println(e);
        }
    }

    public double[] getPixels()
    {
        return pixels;
    }

}
