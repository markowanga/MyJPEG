import huffmanAlgorithm.*;
import javafx.util.Pair;

import java.io.*;

/**
 * Created by Marcin on 01.01.2017.
 * <p>
 * Odtwarzamy z pliku int[][][]
 * <p>
 * Dane zapisane jak przy CompressedImageSaver
 */
public class CompressedImageReader {

    /**
     * Read the compressed image
     *
     * @param fileName - file with compressed image
     * @return - Pair<zigzak blocks, Pair<height, width>>
     */
    public Pair<int[][][], Pair<Pair<Integer, Integer>, Integer>> readImage(String fileName) {
        int[][][] tabToReturn;
        try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            BitInputStream bitInputStream = new BitInputStream(inputStream);
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            int height = dataInputStream.readInt();
            int width = dataInputStream.readInt();
            int minVal = dataInputStream.readInt();
            int maxVal = dataInputStream.readInt();
            int quantizationCode = dataInputStream.readInt();
            int countOfBlocks = (width / 8) * (height / 8);

            CanonicalCode canonCode = readCodeLengthTable(bitInputStream, maxVal - minVal + 2);
            CodeTree code = canonCode.toCodeTree();
            ValuesStream valuesStream = new ValuesStream(bitInputStream, code, maxVal - minVal + 1, minVal);

            tabToReturn = new int[countOfBlocks][3][64];
            fillReturnTable(valuesStream, tabToReturn);

            dataInputStream.close();
            bitInputStream.close();

            return new Pair<>(tabToReturn, new Pair<>(new Pair<>(height, width), quantizationCode));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fill zigzak tables with values
     *
     * @param stream - stream with values to fill
     * @param table  - zigzak table to fill
     * @throws IOException
     */
    private void fillReturnTable(ValuesStream stream, int[][][] table) throws IOException {
        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < 3; j++) {
                table[i][j][0] = stream.getNextValue();
            }

        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 1; k < 64; k++) {
                    table[i][j][k] = stream.getNextValue();
                }
    }

    /**
     * Read the hufman code of values
     *
     * @param in        - stream to read data
     * @param keysCount - count of keys to read
     * @return - object with huffman code
     * @throws IOException
     */
    private CanonicalCode readCodeLengthTable(BitInputStream in, int keysCount) throws IOException {
        int[] codeLengths = new int[keysCount];

        for (int i = 0; i < keysCount; i++) {
            int val = 0;
            for (int j = 0; j < 8; j++)
                val = (val << 1) | in.readNoEof();
            codeLengths[i] = val;
        }

        return new CanonicalCode(codeLengths);
    }

    /**
     * Class to read the coded data (by huffman algorithm)
     * The words have different lenght and was moved (by substract const)
     * Additionaly was compressed to format [zeros count][non zeros count][non zero values...]
     * <p>
     * Outside this class we should use only constructor and getNextValue()
     */
    private class ValuesStream {
        // fields
        private boolean isEndOfStream;
        private int zeroCount;
        private int nonZeroCount;
        private int eofCode;
        private int valueShift;
        private HuffmanDecoder dec;

        /**
         * Constructor
         *
         * @param in       - inputstream
         * @param codeTree - is used to decode the data
         * @param EOFCode  - ending stream code
         * @param minValue - data is shifted about this parameter
         * @throws IOException
         */
        ValuesStream(BitInputStream in, CodeTree codeTree, int EOFCode, int minValue) throws IOException {
            dec = new HuffmanDecoder(in);
            dec.codeTree = codeTree;
            eofCode = EOFCode + minValue;
            isEndOfStream = false;
            valueShift = minValue;
            readNewBlock();
        }

        /**
         * reads new block of data
         *
         * @throws IOException
         */
        private void readNewBlock() throws IOException {
            zeroCount = readNextValueFromFile();
            if (zeroCount == eofCode) {
                isEndOfStream = true;
                return;
            }
            nonZeroCount = readNextValueFromFile();
            if (zeroCount == 0 && nonZeroCount == 0)
                throw new IOException("zeroCount and nonZeroCount equals zero");
        }

        /**
         * Return the next value to zigzak tables
         *
         * @return - value to table
         * @throws IOException
         */
        int getNextValue() throws IOException {
            if (isEndOfStream)
                throw new IOException("No data to return");

            if (zeroCount != 0) {
                zeroCount--;
                if (zeroCount == 0 && nonZeroCount == 0)
                    readNewBlock();
                return 0;
            } else {
                if (nonZeroCount == 0)
                    throw new IOException("No nonZero data");
                nonZeroCount--;
                if (nonZeroCount == 0) {
                    int toReturn = readNextValueFromFile();
                    readNewBlock();
                    return toReturn;
                } else
                    return readNextValueFromFile();
            }
        }

        /**
         * Read the data from file, but it is still compressed in blocks
         *
         * @return - value compressed in blocks
         * @throws IOException
         */
        private int readNextValueFromFile() throws IOException {
            return dec.read() + valueShift;
        }
    }
}