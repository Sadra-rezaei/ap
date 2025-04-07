package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class EX2_PM_1_3 {
    public static void printMatrix(String[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int k = sc.nextInt();

        int c;
        while (true) {
            System.out.print("Enter the number of dots: ");
            c = sc.nextInt();
            if (c >= k * k)
                System.out.println("=== Your number should be less than " + k * k + "! ===");

            else
                break;
        }

        String[][] matrix = new String[k + 2][k + 2];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == 0 || j == 0 || i == matrix.length - 1 || j == matrix.length - 1) {
                    matrix[i][j] = "*";
                }
                else {
                    matrix[i][j] = " ";
                }
            }
        }

        Random rand = new Random();
        int tempRow;
        int tempCol;
        for (int i = 0; i < c; i++) {
            while (true) {
                tempRow = rand.nextInt(k + 1) + 1;
                tempCol = rand.nextInt(k + 1) + 1;

                if (!matrix[tempRow][tempCol].equals("*")) {
                    matrix[tempRow][tempCol] = "*";
                    break;
                }
            }
        }

        printMatrix(matrix);
    }
}
