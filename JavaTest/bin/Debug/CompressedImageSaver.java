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
    public static final int valuesShift = 4100;

    public void saveImage(String fileName, int height, int width, int[][][] zigzakTable) {
        ValuesStream inputHuffmanStream = new ValuesStream(zigzakTable);
        int[] preliminaryFrequency = new int[0];
        try {
            preliminaryFrequency = preparePreliminaryFrequencyTable(inputHuffmanStream);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int minVal = 0, maxVal = 8199;

        while (preliminaryFrequency[minVal] == 0) minVal++;
        while (preliminaryFrequency[maxVal] == 0) maxVal--;
        System.out.println(minVal + " -- " + maxVal);
        preliminaryFrequency[maxVal + 1]++; // add EOF
        System.out.println("EOF code" + (maxVal + 1));

        FrequencyTable frequencyTable = new FrequencyTable(Arrays.copyOfRange(preliminaryFrequency, minVal, maxVal + 2));
        minVal -= valuesShift;
        maxVal -= valuesShift;
        System.out.println("<" + minVal + ", " + maxVal + ">");

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
            dataOutputStream.flush();
            //dataOutputStream.close();

            writeCodeLengthTable(bitOutputStream, canonicalCode);
            compress(code, inputHuffmanStream, bitOutputStream, minVal, maxVal + 1);
            bitOutputStream.close();
            dataOutputStream.close();
            // we do not close the bufferedOutputStream because bitOutputStream do it
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int[] preparePreliminaryFrequencyTable(ValuesStream valuesStream) throws IOException {
        int[] freqTab = new int[8200];
        Arrays.fill(freqTab, 0);
        valuesStream.resetStream();

        int i = 0;
        while (!valuesStream.isEndOfStream()) {
            freqTab[valuesStream.getNextValue() + valuesShift]++;
            i++;
        }

        System.out.println("Ilość wartości w strumieniu: " + i);

        return freqTab;
    }

    void writeCodeLengthTable(BitOutputStream out, CanonicalCode canonCode) throws IOException {
        System.out.println("ilosc kluczy: " + canonCode.getSymbolLimit());
        for (int i = 0; i < canonCode.getSymbolLimit(); i++) {
            int val = canonCode.getCodeLength(i);
            // Write value as 8 bits in big endian
            for (int j = 7; j >= 0; j--)
                out.write((val >>> j) & 1);
        }
    }

    static void compress(CodeTree code, ValuesStream valuesStream, BitOutputStream out, int codeTreeShift, int EOFValue) throws IOException {
        HuffmanEncoder enc = new HuffmanEncoder(out);
        enc.codeTree = code;
        valuesStream.resetStream();

        valuesStream.resetStream();

        int i = 0;
        while (!valuesStream.isEndOfStream()) {
            enc.write(valuesStream.getNextValue() - codeTreeShift);
            //System.out.println("zapis" + i++);
            i++;
        }

        System.out.println("Ilość zapisów do pliku: " + i);

        System.out.println("\nKoniec zapisu do pliku");

        enc.write(EOFValue - codeTreeShift);
    }

    private class ValuesStream {
        private int[][][] toStream; // tablica wejściowa

        private boolean isEndOfInputValues; // czy koniec wartości wejściowych
        private boolean isEndOfOutputValues;
        private boolean streamZeroCounts; // czy podać ilość zer w strumieniu
        private boolean streamNonZeroCounts; // czy podać ilość elementów nie zerowych

        private int zeroCounts; // liczba zer
        private int nonZeroCounts; // liczba niezrowych liczb

        private int[] currentIndex;
        private int[] pozitionToNextStart;

        int iloscDanych = 0;

        ValuesStream(int[][][] ToStream) {
            toStream = ToStream;
            pozitionToNextStart = new int[]{0, 0, 0};
            currentIndex = new int[3];
            isEndOfInputValues = false;
            isEndOfOutputValues = false;
            prepareFirstBlockOfData();
        }

        void resetStream() {
            pozitionToNextStart = new int[]{0, 0, 0};
            isEndOfInputValues = false;
            isEndOfOutputValues = false;
            prepareFirstBlockOfData();
            iloscDanych = 0;
        }

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
            // maksymalna wartość ilości może przekroczyć 4100 (maksymalny wyraz jaki kodujemy w huffmanie)
            // więc trzeba nie przekroczyć
            while (!isEndOfInputValues && zeroCounts < 4096
                    && toStream[pozitionToNextStart[0]][pozitionToNextStart[1]][pozitionToNextStart[2]] == 0) {
                zeroCounts++;
                isEndOfInputValues = nextIndex(pozitionToNextStart);
            }

            // ustawienie current index
            currentIndex[0] = pozitionToNextStart[0];
            currentIndex[1] = pozitionToNextStart[1];
            currentIndex[2] = pozitionToNextStart[2];

            // liczba nie zer
            nonZeroCounts = 0;
            while (!isEndOfInputValues && nonZeroCounts < 4096
                    && toStream[pozitionToNextStart[0]][pozitionToNextStart[1]][pozitionToNextStart[2]] != 0) {
                nonZeroCounts++;
                isEndOfInputValues = nextIndex(pozitionToNextStart);
            }
        }

        int getNextValue() throws IOException {
            if (isEndOfOutputValues)
                throw new IOException("No value to get");
            if (streamZeroCounts) {
                streamZeroCounts = false;
                if (iloscDanych < 100)
                    System.out.println(iloscDanych++ + ": " + zeroCounts);
                return zeroCounts;
            } else if (streamNonZeroCounts) {
                streamNonZeroCounts = false;
                if (nonZeroCounts == 0) {
                    if (isEndOfInputValues) isEndOfOutputValues = true;
                    prepareNextBlokOfData();
                    if (iloscDanych < 100)
                        System.out.println(iloscDanych++ + ": " + 0);
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