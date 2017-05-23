import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-12-30.
 * <p>
 * Class to code the image
 * Class needs only a name of image, and name of file to save compressed
 */
public class Coder {
    //fields
    public static final String fileExtension = ".dmjpg";

    /**
     * Read image to class from fileName
     * Attention: it is a coder - it reads only traditional image files, without the file compressed by our algorithm
     */
    private static BufferedImage readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("Podany plik nie istnieje");
        }
        return null;
    }

    /**
     * Part the image for blocks and save it for blocks
     * if the image have the size not dividable by 8 the remainder will be cutted
     *
     * @throws Exception - when the image is not initialized
     */
    private static List<Blok> splitIntoBlocks(BufferedImage bufferedImage) throws Exception {
        List<Blok> blocks = new ArrayList<>();
        int blockSize = 8;
        int xMult = bufferedImage.getWidth() / 8;
        int yMult = bufferedImage.getHeight() / 8;

        for (int i = 0; i < xMult; i++)
            for (int j = 0; j < yMult; j++) {
                blocks.add(new Blok(bufferedImage, i * blockSize, i * blockSize + 7, j * blockSize, j * blockSize + 7));
            }

        return blocks;
    }

    /**
     * This function match better in coder, but it is here, because we use it to test where
     * we only encode and decode without saving data in file
     *
     * @param base     - image where we want to put the block
     * @param toInsert - image of block which we want insert
     * @param fromX    - x index to start insert block
     * @param fromY    - y index to start insert block
     */
    public static void insertImage(BufferedImage base, BufferedImage toInsert, int fromX, int fromY) {
        int xLimit = toInsert.getWidth() + fromX;
        int yLimit = toInsert.getHeight() + fromY;

        for (int i = fromX; i < xLimit; i++)
            for (int j = fromY; j < yLimit; j++)
                base.setRGB(i, j, toInsert.getRGB(i - fromX, j - fromY));
    }

    /**
     * This function save uncompressed image into compressed file
     *
     * @param sourceFile     - file with source image
     * @param fileNameToSave - file to compress image
     */
    public static void saveCompressedImage(String sourceFile, String fileNameToSave, int quantizationCode) {
        int[][][] tablesToSave;

        // check that fileNameToSave has good fileExtension
        if (fileNameToSave.substring(fileNameToSave.length() - fileExtension.length(),
                fileNameToSave.length() - 1).equals(fileExtension)) {
            System.out.print("File: " + fileNameToSave + " has bad extension. SAVE ABROTED");
            return;
        }

        BufferedImage image = readImage(sourceFile);
        try {
            System.out.println("Start image compressing");
            List<Blok> blocks = splitIntoBlocks(image);
            tablesToSave = new int[blocks.size()][][];
            int[][] quantizationY = QuantizationTable.getQuantizationTableForY(quantizationCode);
            int[][] quantizationCh = QuantizationTable.getQuantizationTableForCh(quantizationCode);

            for (int i = 0; i < blocks.size(); i++) {
                if (i % 10000 == 0) System.out.println(i + " / " + blocks.size());
                tablesToSave[i] = blocks.get(i).encode(quantizationY, quantizationCh);
            }

            System.out.println("Image compressed\nSaving into file");
            new CompressedImageSaver()
                    .saveImage(fileNameToSave, image.getHeight(), image.getWidth(), tablesToSave, quantizationCode);
            System.out.println("Image encoded saved successful");
        } catch (Exception ex) {
            System.out.println(ex.getClass().toString());
        }
    }
}