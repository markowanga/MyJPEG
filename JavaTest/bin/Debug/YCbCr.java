import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin on 31.12.2016.
 */
public class YCbCr {

    static double[] toYCbCr(Color color) {
        double Y, Cb, Cr;
        Y = 0 + 0.299 * color.getRed() + 0.578 * color.getGreen() + 0.114 * color.getBlue();
        Cb = 128 - 0.168736 * color.getRed() - 0.331264 * color.getGreen() + 0.5 * color.getBlue();
        Cr = 128 + 0.5 * color.getRed() - 0.418688 * color.getGreen() - 0.081312 * color.getBlue();
        return new double[]{Y, Cb, Cr};
    }

    static Color toRGB(double[] tab) {
        double Y = tab[0], Cb = tab[1], Cr = tab[2];
        int R, G, B;
        R = (int)(Y + 1.402 * (Cr - 128));
        G = (int)(Y - 0.344136 * (Cb - 128) - 0.714136 * (Cr - 128));
        B = (int)(Y + 1.772 * (Cb - 128));
        return new Color(R, G, B);
    }

    static void zapiszObrazPoPrzejsciach(String nazwa) {
        try {
            BufferedImage image = ImageIO.read(new File(nazwa));
            for (int i = 0; i<image.getHeight(); i++)
                for (int j = 0; j<image.getWidth(); j++)
                    image.setRGB(j, i, toRGB(toYCbCr(new Color(image.getRGB(j, i), true))).getRGB());
            ImageIO.write(image, "bmp", new File("zamiana-" + nazwa));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * nie działa, problem z zamianą - wodocznie tak nie można :P
     */
    static void podzielYCbCr(String nazwa) {
        try {
            BufferedImage image = ImageIO.read(new File(nazwa));
            BufferedImage y_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            BufferedImage Cb_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            BufferedImage Cr_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i<image.getHeight(); i++)
                for (int j = 0; j<image.getWidth(); j++)
                {
                    double[] tab = toYCbCr(new Color(image.getRGB(j, i), true));
                    y_image.setRGB(j, i, toRGB(new double[]{tab[0], 0, 0}).getRGB());
                    Cb_image.setRGB(j, i, toRGB(new double[]{tab[0], tab[1], 0}).getRGB());
                    Cr_image.setRGB(j, i, toRGB(new double[]{tab[0], 0, tab[2]}).getRGB());
                }
            ImageIO.write(y_image, "bmp", new File("y-" + nazwa));
            ImageIO.write(Cb_image, "bmp", new File("Cb-" + nazwa));
            ImageIO.write(Cr_image, "bmp", new File("Cr-" + nazwa));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        zapiszObrazPoPrzejsciach("zdjecie.bmp");
    }
}