/**
 * Created by Marcin on 2017-01-03.
 */
public class ProgramJPEG {

    /**
     * Pierwszy arg: 1 - kompresja, 2 - dekompresja, 3 - kompresja i dekompresja
     * Pozostałe parametry:
     * kompresja: [nazwa pliku wejsciowego][nazwa pliku do zapisu skompresowanego obrazu][kod kompresji]
     * dekompresja: [nazwa pliku skompresowanego][nazwa pliku do zapisu]
     * to i to: [nazwa pliku wejsciowego][nazwa pliku do zapisu skompresowanego obrazu]
     * [nazwa pliku po dekompresji][kod kompresji]
     */
    public static void main(String args[]) {
        int valToDo = Integer.parseInt(args[0]);

        if (valToDo == 1)
            Coder.saveCompressedImage(args[1], args[2], Integer.parseInt(args[3]));
        else if (valToDo == 2)
            Decoder.readCompressedImage(args[1], args[2]);
        else if (valToDo == 3) {
            Coder.saveCompressedImage(args[1], args[2], Integer.parseInt(args[4]));
            Decoder.readCompressedImage(args[2], args[3]);
        }
    }
}