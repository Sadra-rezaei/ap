package ap.exercises.ex2;

import java.util.Scanner;

public class EX2_PM_1_2 {
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

        printMatrix(matrix);
    }
}
