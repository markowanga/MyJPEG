import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-12-30.
 *
 * Class to code the image
 * Class needs only a name of image, and name of
 */
public class Coder {
    public static final String fileExtension = ".dmjpg";

    private BufferedImage image;
    private boolean initialized = false;
    private List<Blok> blocks;
    FileOutputStream stream;
    DataOutputStream dataOutputStream;
    // TODO: 01.01.2017 Nie potrzebujemy listy obiektów jak kodyjemy i zapisujemy jeden po drugim, można nawet pomyśleć
    // o statycznych metodach do kodowania
    // bo prkatycznie coder tylko koduje - nic więcej,
    // możemy jak coś zakodować kilka razy, ale nie wiem czy to ma zbytnio sens


    Coder() {
        blocks = new ArrayList<>();
    }

    /**
     * Read image to class from fileName
     * Attention: it is a coder - it reads only traditional image files, without the file compressed by our algorithm
     */
    public void readImage(String filePath) {
        try {
            this.image = ImageIO.read(new File(filePath));
            this.initialized = true;

        } catch (IOException e) {
            System.err.println("Podany plik nie istnieje");
        }
    }

    /**
     * Part the image for blocks and save it for blocks
     * @throws Exception - when the image is not initialized
     */
    public void splitIntoBlocks() throws Exception {
        // TODO: 01.01.2017 Błędne granice i nie zadbano o to jak krawędzie nie są podzielne przez 8 -- na pierwszy rzut oka
        if (initialized) {
            int fromX = 0;
            int formY = 0;
            int blockSize = 8;
            int xMult = image.getWidth() / 8;
            int yMult = image.getHeight() / 8;
            //int x=0;
            //int y=0;
            for (int i = 0; i < xMult; i++) {
                for (int j = 0; j < yMult; j++) {
                    blocks.add(new Blok(image, i * blockSize, i * blockSize + 7, j * blockSize, j * blockSize + 7));
                }
            }
        } else throw new Exception("Not initialized");

    }

    /**
     * This function match better in coder, but it is here, because we use it to test where
     * we only encode and decode without saving data in file
     * @param base - image where we want to put the block
     * @param toInsert - image of block which we want insert
     * @param fromX - x index to start insert block
     * @param fromY - y index to start insert block
     */
    public static void insertImage(BufferedImage base, BufferedImage toInsert, int fromX, int fromY) {
        // TODO: 01.01.2017 Zastosować ewentualne poprawki ze splitIntoBlocks jak zostanie zmieniona
        int xLimit = toInsert.getWidth() + fromX;
        int yLimit = toInsert.getHeight() + fromY;

        for (int i = fromX; i < xLimit; i++)
            for (int j = fromY; j < yLimit; j++)
                base.setRGB(i, j, toInsert.getRGB(i - fromX, j - fromY));
    }

    /**
     * Function which test the encoding and decoding
     * Read the image and save after compression and decompression - we can perfectly see the loss of quality
     */
    public static void test(String fileName) {
        int minVal = 0, maxVal = 0;
        Coder coder = new Coder();
        coder.readImage(fileName);
        try {
            coder.splitIntoBlocks();
            BufferedImage toSave = new BufferedImage(coder.image.getWidth(), coder.image.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < coder.blocks.size(); i++) {
                if (i%10000==0) System.out.println(i + " / " + coder.blocks.size());
                int[][] tab = coder.blocks.get(i).encode();
                for (int a=0; a<3; a++)
                    for (int b = 0; b<64; b++)
                    {
                        if (tab[a][b]>maxVal)
                            maxVal = tab[a][b];
                        else if (tab[a][b]<minVal)
                            minVal = tab[a][b];
                    }
                coder.blocks.get(i).testConversion();
            }
            System.out.println("<" + minVal + ", " + maxVal + ">");
            int n = 0;
            int xMult = coder.image.getWidth() / 8;
            int yMult = coder.image.getHeight() / 8;
            for (int i = 0; i < xMult; i++) {
                for (int j = 0; j < yMult; j++) {
                    insertImage(toSave, (coder.blocks.get(n)).toRGBArray(), i * Blok.blockSize, Blok.blockSize * j);
                    n++;
                }
            }
            try {
                ImageIO.write(toSave, "jpg", new File("encoded-decoded-" + fileName));
                System.out.println("Obraz: " + "encoded-decoded-" + fileName + " saved successful\n------------\n\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("ERROR WHILE SAVING");
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().toString());
        }
    }


    /**
     * This function save uncompressed image into compressed file
     */
    public void saveCompressedImage(String sourceFile, String fileNameToSave) {
        int[][][] tablesToSave;

        // check that fileNameToSave has good fileExtension
        if (fileNameToSave.substring(fileNameToSave.length()-fileExtension.length(),
                fileNameToSave.length()-1).equals(fileExtension)) {
            System.out.print("File: " + fileNameToSave + " has bad extension. SAVE ABROTED");
            return;
        }
        Coder coder = new Coder();
        coder.readImage(sourceFile);
        try {
            System.out.print("Start image compressing");
            coder.splitIntoBlocks();
            tablesToSave = new int[coder.blocks.size()][][];
            for (int i = 0; i < coder.blocks.size(); i++) {
                if (i%10000==0) System.out.println(i + " / " + coder.blocks.size());
                tablesToSave[i] = coder.blocks.get(i).encode();
            }
            System.out.println("Image compressed\nSaving into file");
            new CompressedImageSaver().saveImage(fileNameToSave, coder.getHeight(), coder.getWidth(), tablesToSave);
            // tu fukcja zapisu
        } catch (Exception ex) {
            System.out.println(ex.getClass().toString());
        }
    }

    /**
     * This function save uncompressed image into compressed file
     */
    public void porownajZigzakPrzedIPo(String sourceFile, String fileNameToSave) {
        int[][][] tablesToSave;

        // check that fileNameToSave has good fileExtension
        if (fileNameToSave.substring(fileNameToSave.length()-fileExtension.length(),
                fileNameToSave.length()-1).equals(fileExtension)) {
            System.out.print("File: " + fileNameToSave + " has bad extension. SAVE ABROTED");
            return;
        }
        Coder coder = new Coder();
        coder.readImage(sourceFile);
        try {
            System.out.print("Start image compressing");
            coder.splitIntoBlocks();
            tablesToSave = new int[coder.blocks.size()][][];
            for (int i = 0; i < coder.blocks.size(); i++) {
                if (i%10000==0) System.out.println(i + " / " + coder.blocks.size());
                tablesToSave[i] = coder.blocks.get(i).encode();
            }
            System.out.println("Image compressed\nSaving into file");
            new CompressedImageSaver().saveImage(fileNameToSave, coder.getHeight(), coder.getWidth(), tablesToSave);
            int[][][] tableReaded = new CompressedImageReader().readImage(fileNameToSave).getKey();
            System.out.println("Czy Tablice takie same: " + tableReaded.equals(tablesToSave));
            System.out.println("RozmiaryTablic: " + tablesToSave.length + " - " + tableReaded.length);
            // tu fukcja zapisu
            for (int a=0; a<10; a++)
            {
                for (int b = 0; b<3; b++)
                {
                    for (int c = 0; c<64; c++)
                        System.out.print(tablesToSave[a][b][c] + ", ");
                    System.out.println();
                    for (int c = 0; c<64; c++)
                        System.out.print(tableReaded[a][b][c] + ", ");
                    System.out.println();
                    System.out.println();
                }

                System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().toString());
        }
    }

    int getHeight() {
        return image.getHeight();
    }

    int getWidth() {
        return image.getWidth();
    }
}
