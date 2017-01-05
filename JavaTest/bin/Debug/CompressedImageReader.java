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

    public Pair<int[][][], Pair<Integer, Integer>> readImage(String fileName) {
        System.out.println("\nZACZYNAM ODCZYT\n");
        int[][][] tabToReturn = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            BitInputStream bitInputStream = new BitInputStream(inputStream);
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            int height = dataInputStream.readInt();
            int width = dataInputStream.readInt();
            int minVal = dataInputStream.readInt();
            int maxVal = dataInputStream.readInt();
            int countOfBlocks = (width / 8) * (height / 8);

            CanonicalCode canonCode = readCodeLengthTable(bitInputStream, maxVal - minVal + 2);
            CodeTree code = canonCode.toCodeTree();
            ValuesStream valuesStream = new ValuesStream(bitInputStream, code, maxVal - minVal + 1, minVal);

            tabToReturn = new int[countOfBlocks][3][64];
            fillReturnTable(valuesStream, tabToReturn);

            dataInputStream.close();
            bitInputStream.close();

            return new Pair<>(tabToReturn, new Pair<>(height, width));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair<>(tabToReturn, new Pair<>(-1, -1));
    }

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
     * ta klasa podaje kolejno odczytanne wartości
     * w konstruktorze podajemy słownik do odczytu
     * najłatwiej odczytywać dane za pomocą szukania czy jest taki klucz w drzewie
     * jak nie ma to szukamy dalej
     * <p>
     * można alternatywnie zrobić własne drzewo i wraz z napływającymi danymi poruszać się po drzewie
     * ale to trudniejsze w implementacji - wtedy mozna zmienić struktórę na List w przechowywaniu słownika
     */
    private class ValuesStream {
        boolean isEndOfStream;
        int zeroCount;
        int nonZeroCount;
        int eofCode;
        int valueShift;
        HuffmanDecoder dec;

        ValuesStream(BitInputStream in, CodeTree codeTree, int EOFCode, int minValue) throws IOException {
            dec = new HuffmanDecoder(in);
            dec.codeTree = codeTree;
            eofCode = EOFCode + minValue;
            isEndOfStream = false;
            valueShift = minValue;
            readNewBlock();
        }

        void readNewBlock() throws IOException {
            zeroCount = readNextValueFromFile();
            if (zeroCount == eofCode) {
                isEndOfStream = true;
                return;
            }
            nonZeroCount = readNextValueFromFile();
            if (zeroCount == 0 && nonZeroCount == 0)
                throw new IOException("zeroCount and nonZeroCount equals zero");
        }

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

        int readNextValueFromFile() throws IOException {
            return dec.read() + valueShift;
        }
    }
}