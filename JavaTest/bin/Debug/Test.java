/**
 * Created by Daniel on 2016-12-30.
 */
public class Test {
    static void testujZapisOdczytDoPliku(String source, String posredni, String koncowy) {
        new Coder().saveCompressedImage(source, posredni);
        new Decoder().readCompressedImage(posredni, koncowy);
    }

    public static void main(String args[]) {
        try{
            //testujZapisOdczytDoPliku("photo1.bmp", "compressed-photo33.dmjpg", "poOdczycie33.bmp");
            testujZapisOdczytDoPliku(args[0], args[1], args[2]);
        }
         catch (Exception ex)
        {
            System.err.println("Wrong args in initialization !!!");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

    }
}