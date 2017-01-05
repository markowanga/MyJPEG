import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Daniel on 2016-12-31.
 */
public class Decoder {

    /**
     * odwrotnie jak w przypadku Codera
     *
     * @param sourceFile
     * @param fileNameToSave
     */
    public void readCompressedImage(String sourceFile, String fileNameToSave) {
        Pair<int[][][], Pair<Integer, Integer>> pair = new CompressedImageReader().readImage(sourceFile);
        int width = pair.getValue().getValue();
        int heigth = pair.getValue().getKey();
        BufferedImage toSave = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        int[][][] sourceTable = pair.getKey();

        int xMult = width / 8;
        int yMult = heigth / 8;

        for (int i = 0, n = 0; i < xMult; i++) {
            for (int j = 0; j < yMult; j++, n++) {
                Coder.insertImage(toSave, new Blok(sourceTable[n]).toRGBArray(), i * Blok.blockSize, Blok.blockSize * j);
            }
        }

        try {
            ImageIO.write(toSave, "bmp", new File(fileNameToSave));
            System.out.println("Obraz: " + "encoded-decoded-" + fileNameToSave + " saved successful\n------------\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR WHILE SAVING");
        }
    }
}
