/**
 * Created by Marcin on 31.12.2016.
 * <p>
 * To ma sens bo tego bedzie więcej, bo pokazać można większe straty i mniejsze
 */
public class QuantizationTable {
    private static int[][] quantizationTableForYNo1 = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 56},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}};

    private static int[][] quantizationTableForChNo1 = {
            {17, 18, 24, 47, 24, 40, 51, 61},
            {18, 21, 26, 66, 26, 58, 60, 56},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}};

    private static int[][] multiplayMatrix(int[][] matrix, double x) {
        int[][] toReturn = new int[8][8];

        for (int a = 0; a < 8; a++)
            for (int b = 0; b < 8; b++)
                toReturn[a][b] = (int) (matrix[a][b] * x);

        return toReturn;
    }

    private static int[][] getQuantizationTable(int[][] matrix, int choose) {
        if (choose == 1)
            return multiplayMatrix(matrix, 0.5);
        else if (choose == 2)
            return matrix;
        else if (choose == 3)
            return multiplayMatrix(matrix, 2.5);
        else if (choose == 4)
            return multiplayMatrix(matrix, 5);
        else if (choose == 5)
            return multiplayMatrix(matrix, 8);
        else if (choose == 6)
            return multiplayMatrix(matrix, 12);
        else return null;
    }

    static int[][] getQuantizationTableForY(int index) {
        return getQuantizationTable(quantizationTableForYNo1, index);
    }

    static int[][] getQuantizationTableForCh(int index) {
        return getQuantizationTable(quantizationTableForChNo1, index);
    }
}