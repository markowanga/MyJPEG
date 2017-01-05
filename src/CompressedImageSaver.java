import huffmanAlgorithm.*;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Marcin on 01.01.2017.
 * <p>
 * Save the compressed image (int[][][]) into file
 * <p>
 * na początku pliku mamy dane
 * [wys] [szer] [minimalny element rozkodowanego ciągu] [max element zakodowanego ciągu]
 * Dane które będziemy kodować trzeba najpierw przekształcić
 * na samym początku trzeba zmienić ich kolejność
 * najpierw zapisjemy kolejno zerowe elementy tablicy
 * potem kolejno elementy 1-63
 * wzór:
 * // pierwsza część int[0][0][0], ..., int[0][2][0], int[1][0][0], ..., int[ilość elementów-1][2][0]
 * // druga część: int[0][0][1->63], ..., int[0][2][1->63], int[1][0][1-63], ..., int[ilość elementów-1][2][1->63]
 * <p>
 * Następnie Całe te dane układamy w bloki
 * [ilość zer][ilość nie zer][wartości nie będące zerami]
 * <p>
 * Taki ostateczny ciąg kodujemy huffmanem i zapisujemy w dlaszej części pliku
 */
public class CompressedImageSaver {
    // fields
    public static final int valuesShift = 4100;

    /**
     * This function can save compressed image into file
     *
     * @param fileName - file name in which we save
     * @param height - height of picture
     * @param width - width of picture
     * @param zigzakTable - the table of zigzaks
     * @param quantizationCode - quantizationCode to save
     */
    public void saveImage(String fileName, int height, int width, int[][][] zigzakTable, int quantizationCode) {
        ValuesStream inputHuffmanStream = new ValuesStream(zigzakTable);
        int[] preliminaryFrequency;
        int minVal = 0, maxVal = 8199;

        try {
            preliminaryFrequency = preparePreliminaryFrequencyTable(inputHuffmanStream);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (preliminaryFrequency[minVal] == 0) minVal++;
        while (preliminaryFrequency[maxVal] == 0) maxVal--;
        preliminaryFrequency[maxVal + 1]++; // add EOF

        FrequencyTable frequencyTable = new FrequencyTable(Arrays.copyOfRange(preliminaryFrequency, minVal, maxVal + 2));
        minVal -= valuesShift;
        maxVal -= valuesShift;

        CodeTree code = frequencyTable.buildCodeTree();
        CanonicalCode canonicalCode = new CanonicalCode(code, maxVal - minVal + 2);
        code = canonicalCode.toCodeTree();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            BitOutputStream bitOutputStream = new BitOutputStream(new BufferedOutputStream(fileOutputStream));
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

            dataOutputStream.writeInt(height);
            dataOutputStream.writeInt(width);
            dataOutputStream.writeInt(minVal);
            dataOutputStream.writeInt(maxVal);
            dataOutputStream.writeInt(quantizationCode);
            dataOutputStream.flush();

            writeCodeLengthTable(bitOutputStream, canonicalCode);
            compress(code, inputHuffmanStream, bitOutputStream, minVal, maxVal + 1);
            bitOutputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepate the table of frequency of data (how many times the value is in stream)
     * We shift the value because there is no negative index, and the minimal value in stream equals -4080 and max 4080
     * (we do not count EOF - the maxValue + 1 of the stream)
     * @param valuesStream - stream with values
     * @return - int[] table with frequency
     * @throws IOException
     */
    int[] preparePreliminaryFrequencyTable(ValuesStream valuesStream) throws IOException {
        int[] freqTab = new int[8200];
        Arrays.fill(freqTab, 0);
        valuesStream.resetStream();

        while (!valuesStream.isEndOfStream()) {
            freqTab[valuesStream.getNextValue() + valuesShift]++;
        }

        return freqTab;
    }

    /**
     * Save in file the dictionary of huffman code
     *
     * @param out - stream to save
     * @param canonCode - object with dictionary
     * @throws IOException
     */
    void writeCodeLengthTable(BitOutputStream out, CanonicalCode canonCode) throws IOException {
        for (int i = 0; i < canonCode.getSymbolLimit(); i++) {
            int val = canonCode.getCodeLength(i);
            // Write value as 8 bits in big endian
            for (int j = 7; j >= 0; j--)
                out.write((val >>> j) & 1);
        }
    }

    /**
     * Save in file compressed data from stream using huffman algorithm
     *
     * @param code - Huffman dictionary
     * @param valuesStream - stream with values
     * @param out - stream to write values
     * @param codeTreeShift - the value to move values (we can't save negative values
     *                      using this implementation of Huffman algorithm)
     * @param EOFValue - this value mean End Of File - it is very important while reading data
     * @throws IOException
     */
    static void compress(CodeTree code, ValuesStream valuesStream, BitOutputStream out, int codeTreeShift, int EOFValue) throws IOException {
        HuffmanEncoder enc = new HuffmanEncoder(out);
        enc.codeTree = code;
        valuesStream.resetStream();

        while (!valuesStream.isEndOfStream()) {
            enc.write(valuesStream.getNextValue() - codeTreeShift);
        }

        enc.write(EOFValue - codeTreeShift);
    }

    /**
     * This class mainly convent the tables of Zigzak to less count of values changing the order
     */
    private class ValuesStream {
        // fields
        private int[][][] toStream; // zigzak tables

        private boolean isEndOfInputValues;
        private boolean isEndOfOutputValues;
        private boolean streamZeroCounts;
        private boolean streamNonZeroCounts;

        private int zeroCounts;
        private int nonZeroCounts;

        private int[] currentIndex;
        private int[] positionToNextStart;

        /**
         * Condtructor
         * @param ToStream - zigzak tables to stream
         */
        ValuesStream(int[][][] ToStream) {
            toStream = ToStream;
            positionToNextStart = new int[]{0, 0, 0};
            currentIndex = new int[3];
            isEndOfInputValues = false;
            isEndOfOutputValues = false;
            prepareFirstBlockOfData();
        }

        /**
         * Resets the stream, when we want read stream again
         */
        void resetStream() {
            positionToNextStart = new int[]{0, 0, 0};
            isEndOfInputValues = false;
            isEndOfOutputValues = false;
            prepareFirstBlockOfData();
        }

        /**
         * Prepares first block of data
         */
        private void prepareFirstBlockOfData() {
            if (toStream.length == 0) {
                isEndOfInputValues = true;
                streamNonZeroCounts = true;
                streamZeroCounts = true;
                zeroCounts = 0;
                nonZeroCounts = 0;
            } else prepareNextBlokOfData();
        }

        boolean isEndOfStream() {
            return isEndOfOutputValues;
        }

        private boolean nextIndex(int[] indexTable) {
            if (indexTable[2] == 0) {
                if (indexTable[1] == 2) {
                    if (indexTable[0] == toStream.length - 1) {
                        indexTable[0] = 0;
                        indexTable[1] = 0;
                        indexTable[2] = 1;
                    } else {
                        indexTable[0]++;
                        indexTable[1] = 0;
                    }
                } else indexTable[1]++;
            } else {
                if (indexTable[2] != 63)
                    indexTable[2]++;
                else if (indexTable[1] != 2) {
                    indexTable[1]++;
                    indexTable[2] = 1;
                } else {
                    if (indexTable[0] == toStream.length - 1)
                        return true;
                    else {
                        indexTable[0]++;
                        indexTable[1] = 0;
                        indexTable[2] = 1;
                    }
                }
            }
            return false;
        }

        private void prepareNextBlokOfData() {
            streamZeroCounts = true;
            streamNonZeroCounts = true;
            zeroCounts = 0;
            while (!isEndOfInputValues && zeroCounts < 4096
                    && toStream[positionToNextStart[0]][positionToNextStart[1]][positionToNextStart[2]] == 0) {
                zeroCounts++;
                isEndOfInputValues = nextIndex(positionToNextStart);
            }

            // ustawienie current index
            currentIndex[0] = positionToNextStart[0];
            currentIndex[1] = positionToNextStart[1];
            currentIndex[2] = positionToNextStart[2];

            // liczba nie zer
            nonZeroCounts = 0;
            while (!isEndOfInputValues && nonZeroCounts < 4096
                    && toStream[positionToNextStart[0]][positionToNextStart[1]][positionToNextStart[2]] != 0) {
                nonZeroCounts++;
                isEndOfInputValues = nextIndex(positionToNextStart);
            }
        }

        int getNextValue() throws IOException {
            if (isEndOfOutputValues)
                throw new IOException("No value to get");
            if (streamZeroCounts) {
                streamZeroCounts = false;
                return zeroCounts;
            } else if (streamNonZeroCounts) {
                streamNonZeroCounts = false;
                if (nonZeroCounts == 0) {
                    if (isEndOfInputValues) isEndOfOutputValues = true;
                    prepareNextBlokOfData();
                    return 0;
                } else
                    return nonZeroCounts;
            } else {
                int toReturn = toStream[currentIndex[0]][currentIndex[1]][currentIndex[2]];
                if (nonZeroCounts == 1) {
                    if (isEndOfInputValues) isEndOfOutputValues = true;
                    prepareNextBlokOfData();
                } else {
                    nonZeroCounts--;
                    nextIndex(currentIndex);
                }
                return toReturn;
            }
        }
    }
}