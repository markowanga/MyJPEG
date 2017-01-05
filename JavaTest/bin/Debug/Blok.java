import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Daniel on 2016-12-29.
 *
 * Class fully encode and decode the block 8x8
 * from BufferedImage to zigzak (all block is retuned in int[3][] - table of zigzak tables)
 *
 * all theroy is located in theory.pdf
 */
public class Blok {

    static final int blockSize = 8;

    double[][] lum = new double[blockSize][blockSize];
    double[][] chrominanceB = new double[blockSize][blockSize];
    double[][] chrominanceR = new double[blockSize][blockSize];

    ///PART1- COLOURSPACE CONVERTION

    /**
     * In this constructor we get a subimage, and calculate the yCbCr color space
     */
    public Blok(BufferedImage image, int fromX, int toX, int fromY, int toY) {
        BufferedImage img = image.getSubimage(fromX, fromY, toX - fromX + 1, toY - fromY + 1);
        for (int i = 0; i < blockSize; i++)
            for (int j = 0; j < blockSize; j++) {
                Color color = new Color(img.getRGB(i, j), true);
                lum[i][j] = 0 + 0.299 * color.getRed() + 0.578 * color.getGreen() + 0.114 * color.getBlue();
                chrominanceB[i][j] = 128 - 0.168736 * color.getRed() - 0.331264 * color.getGreen() + 0.5 * color.getBlue();
                chrominanceR[i][j] = 128 + 0.5 * color.getRed() - 0.418688 * color.getGreen() - 0.081312 * color.getBlue();
            }
    }

    // TODO: 31.12.2016 ZROBIĆ KONKSTUKTOR DLA WCZYTANEGO I ZAKODOWANEGO BOLKU (MA POBIERAĆ ZIGZAKI)
    public Blok(int[][] source) {
        decode(source);
    }

    /**
     * Convent the matrix with yCbCr color space (lum, chrominanceR, chrominanceB tables)
     * to RGB in returned bufferedImage
     */
    public BufferedImage toRGBArray() {
        BufferedImage image = new BufferedImage(blockSize, blockSize, 9);
        int r = 0, b = 0, g = 0;
        try {
            for (int i = 0; i < blockSize; i++)
                for (int j = 0; j < blockSize; j++) {
                    r = (int) (lum[i][j] + 1.402 * (chrominanceR[i][j] - 128));
                    g = (int) (lum[i][j] - 0.344136 * (chrominanceB[i][j] - 128) - 0.714136 * (chrominanceR[i][j] - 128));
                    b = (int) (lum[i][j] + 1.772 * (chrominanceB[i][j] - 128));

                    r = r > 255 ? 255 : r;
                    b = b > 255 ? 255 : b;
                    g = g > 255 ? 255 : g;

                    Color color = new Color(r < 0 ? 0 : r, g < 0 ? 0 : g, b < 0 ? 0 : b);

                    image.setRGB(i, j, color.getRGB());
                }
        } catch (Exception e) {
            System.err.println("R: " + r + "G: " + g + "B: ");
        }
        return image;
    }

    ///PART2 - DISCRETE COSINE TRANSFORM

    /**
     * function which is used in simpleDTC, the same like form equation form theory.pdf -> slide 6th
     */
    private static double C(int value) {
        return value == 0 ? 1 / Math.sqrt(2) : 1;
    }

    /**
     * Calculates the simple value for DTC matrix
     * The equation is on the 6th slide in theory.pdf -> F(u, V)
     *
     * @param baseMatrix - the base matrix from which we count the DTC
     */
    private static double simpleMyDTC(int u, int v, double[][] baseMatrix) {
        // TODO: 31.12.2016 Zaoszczędzenie tworzenia nowych tablic
        /*
        I sposób: stworzenie tablic jeden raz i przekazywanie ich w parametrach
        II sposób: stworzenie tablic statycznych i zmienianie ich wartości
         */
        double[] xMatrix = new double[blockSize];
        double[] yMatrix = new double[blockSize];
        double temp;
        // calculate the cos() form the equation, because every value is calculated 8 times
        for (int i = 0; i < 8; i++) {
            temp = (0.125 * i + 0.0625) * Math.PI;
            xMatrix[i] = Math.cos(temp * v);
            yMatrix[i] = Math.cos(temp * u);
        }
        temp = 0;
        for (int i = 0; i < blockSize; i++)
            for (int j = 0; j < blockSize; j++)
                temp += baseMatrix[i][j] * yMatrix[i] * xMatrix[j];
        return C(u) * C(v) / 4 * temp;
    }

    /**
     * Calculates DTC optimalized, written by my own
     */
    private static double[][] myDCT(double[][] toTransform) {
        double[][] result = new double[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++)
            for (int j = 0; j < blockSize; j++)
                result[i][j] = simpleMyDTC(i, j, toTransform);
        return result;
    }

    /**
     * Calculates the simple value from DTC matrix
     * The equation is on the 6th slide in theory.pdf -> f(u, V)
     *
     * @param baseMatrix - the base matrix from which we count the DTC
     */
    private static double simpleMyIDTC(int x, int y, double[][] baseMatrix) {
        // TODO: 31.12.2016 Zaoszczędzenie tworzenia nowych tablic
        /*
        I sposób: stworzenie tablic jeden raz i przekazywanie ich w parametrach
        II sposób: stworzenie tablic statycznych i zmienianie ich wartości
         */
        double[] uMatrix = new double[blockSize];
        double[] vMatrix = new double[blockSize];
        double sum = 0, tempX = (0.125 * x + 0.0625) * Math.PI, tempY = (0.125 * y + 0.0625) * Math.PI;
        // calculate the cos() form the equation, because every value is calculated blockSize times
        for (int i = 0; i < blockSize; i++) {
            uMatrix[i] = Math.cos(tempX * i);
            vMatrix[i] = Math.cos(tempY * i);
        }
        for (int u = 0; u < blockSize; u++)
            for (int v = 0; v < blockSize; v++)
                sum += C(u) * C(v) * baseMatrix[u][v] * uMatrix[u] * vMatrix[v];
        return sum / 4;
    }

    /**
     * Calculates IDTC optymalized, written by my own
     * IDTC is a revers od DTC
     */
    private static double[][] myIDTC(double[][] toTransform) {
        double[][] result = new double[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++)
            for (int j = 0; j < blockSize; j++)
                result[i][j] = simpleMyIDTC(i, j, toTransform);
        return result;
    }

    ///PART3 - QUANTIZATION / DEQUANTIZATION

    /**
     * Makes a quantization, this opeartion commpressed the data and makes losses
     *
     * @param table - the table which is quantizated
     * @param Q     - the table which quantizate the @param table
     * @return - quantizated table
     */
    private static int[][] quantization(double[][] table, int[][] Q) {
        int[][] toReturn = new int[blockSize][blockSize];
        for (int x = 0; x < blockSize; x++)
            for (int y = 0; y < blockSize; y++)
                toReturn[x][y] = (int) Math.round(table[x][y] / Q[x][y]);

        return toReturn;
    }

    /**
     * It is function which undo quantization
     * But
     *
     * @param table - the table which is quantizated
     * @param Q     - the table which quantizate the @param table
     * @return - quantizated table
     */
    private static double[][] deQuantization(int[][] table, int[][] Q) {
        double[][] toReturn = new double[blockSize][blockSize];
        for (int x = 0; x < blockSize; x++)
            for (int y = 0; y < blockSize; y++)
                toReturn[x][y] = table[x][y] * Q[x][y];

        return toReturn;
    }

    /// PART4- ZIGZAG/UNZIGZAG

    /**
     * Makes the zigzak form matrix
     */
    private static int[] zigzak(int[][] table) {
        int[] toReturn = new int[blockSize * blockSize];
        int index = 0;

        for (int a = 0; a < 8; a += 2) {
            for (int x = 0, y = a; y >= 0; x++, y--)
                toReturn[index++] = table[x][y];
            for (int x = a + 1, y = 0; x >= 0; x--, y++)
                toReturn[index++] = table[x][y];
        }

        for (int a = 1; a < 7; a += 2) {
            for (int x = a, y = 7; y >= a; x++, y--)
                toReturn[index++] = table[x][y];
            for (int x = 7, y = a + 1; x >= a + 1; x--, y++)
                toReturn[index++] = table[x][y];
        }

        toReturn[index] = table[7][7];
        return toReturn;
    }

    /**
     * Makes the matrix from table, undo zigzak
     */
    private static int[][] dezigzak(int[] table) {
        int[][] toReturn = new int[blockSize][blockSize];
        int index = 0;

        for (int a = 0; a < 8; a += 2) {
            for (int x = 0, y = a; y >= 0; x++, y--)
                toReturn[x][y] = table[index++];
            for (int x = a + 1, y = 0; x >= 0; x--, y++)
                toReturn[x][y] = table[index++];
        }

        for (int a = 1; a < 7; a += 2) {
            for (int x = a, y = 7; y >= a; x++, y--)
                toReturn[x][y] = table[index++];
            for (int x = 7, y = a + 1; x >= a + 1; x--, y++)
                toReturn[x][y] = table[index++];
        }

        toReturn[7][7] = table[index];
        return toReturn;
    }

    /// PART5- ENCODING/DECODING SIMPLE TABLE/MATRIX

    /**
     * Encodes one matrix - makes DCT, quantization, and zigzak
     *
     * @param toEncode          - matrix to encode
     * @param quantizationTable - matrix to quantization
     * @return - zigzak table
     */
    private int[] encodeSimple(double[][] toEncode, int[][] quantizationTable) {
        return zigzak(quantization(myDCT(toEncode), quantizationTable));
    }

    /**
     * Decodes one matrix - undo zigzak, quantization and DCT
     *
     * @param toDecode          - table to decode
     * @param quantizationTable - matrix to quantization
     * @return - decoded matrix
     */
    private double[][] decodeSimple(int[] toDecode, int[][] quantizationTable) {
        return myIDTC(deQuantization(dezigzak(toDecode), quantizationTable));
    }

    /**
     * simply encodes and decodes one matrix
     * good for looking for changes in image for losses data in conversion
     */
    private double[][] testConversionsSimple(double[][] toEncode, int[][] quantizationTable) {
        return myIDTC(myDCT(toEncode));
    }

    /// PART6- ENCODING/DECODING BLOCK

    /**
     * Encodes all the data
     * Calculates the the yCbCr values (i will be put in lum, chrominanceR and chrominanceB tables)
     * to zigzak table
     */
    int[][] encode() {
        int[][] table = new int[3][];
        table[0] = encodeSimple(lum, QuantizationTable.quantizationTableForY);
        table[1] = encodeSimple(chrominanceR, QuantizationTable.quantizationTableForCh);
        table[2] = encodeSimple(chrominanceB, QuantizationTable.quantizationTableForCh);
        return table;
    }

    /**
     * Decodes all the data
     * Takes the zigzak table for every colour encoded color from yCbCr color space,
     * and calculate the the yCbCr values (i will be put in lum, chrominanceR and chrominanceB tables)
     *
     * @param table - elements (0) - encoded lum, (1) - encoded chrominanceR, (2) - encoded chrominanceB
     */
    void decode(int[][] table) {
        lum = decodeSimple(table[0], QuantizationTable.quantizationTableForY);
        chrominanceR = decodeSimple(table[1], QuantizationTable.quantizationTableForCh);
        chrominanceB = decodeSimple(table[2], QuantizationTable.quantizationTableForCh);
    }

    /**
     * Tests the conversion of the block
     * Test all coding and decoding functions to zigzak (without Huffman and etc..)
     */
    void testConversion() {
        lum = testConversionsSimple(lum, QuantizationTable.quantizationTableForY);
        chrominanceR = testConversionsSimple(chrominanceR, QuantizationTable.quantizationTableForCh);
        chrominanceB = testConversionsSimple(chrominanceB, QuantizationTable.quantizationTableForCh);
    }
}