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
    public static void readCompressedImage(String sourceFile, String fileNameToSave) {
        System.out.println("Start image decompressing");
        Pair<int[][][], Pair<Pair<Integer, Integer>, Integer>> pair = new CompressedImageReader().readImage(sourceFile);
        System.out.println("Image read from file");
        int width = pair.getValue().getKey().getValue();
        int heigth = pair.getValue().getKey().getKey();
        int quantizationCode = pair.getValue().getValue();
        int[][] quantizationY = QuantizationTable.getQuantizationTableForY(quantizationCode);
        int[][] quantizationCh = QuantizationTable.getQuantizationTableForCh(quantizationCode);
        BufferedImage toSave = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        int[][][] sourceTable = pair.getKey();

        int xMult = width / 8;
        int yMult = heigth / 8;

        for (int i = 0, n = 0; i < xMult; i++)
            for (int j = 0; j < yMult; j++, n++) {
                if (n % 10000 == 0)
                    System.out.println(n + " / " + sourceTable.length);
                Coder.insertImage(toSave, new Blok(sourceTable[n], quantizationY, quantizationCh).toBufferedImage(),
                        i * Blok.blockSize, Blok.blockSize * j);
            }

        try {
            ImageIO.write(toSave, "bmp", new File(fileNameToSave));
            System.out.println("Decoded image saved successful\n------------\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR WHILE SAVING");
        }
    }
}
