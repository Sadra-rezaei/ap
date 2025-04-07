package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class EX2_PM_1_5 {
    public static void printMatrix(String[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void movement(String[][] matrix, Random rand, int characterRow, int characterCol){
        matrix[characterRow][characterCol] = "X";
        int way = rand.nextInt(3);

        switch(way) {
            case 0:
                System.out.println("UP");
                if (!matrix[characterRow - 1][characterCol].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow - 1][characterCol] = "X";
                    characterRow -= 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 1:
                System.out.println("RIGHT");
                if (!matrix[characterRow][characterCol + 1].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow][characterCol + 1] = "X";
                    characterCol += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 2:
                System.out.println("DOWN");
                if (!matrix[characterRow + 1][characterCol].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow + 1][characterCol] = "X";
                    characterRow += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 3:
                System.out.println("LEFT");
                if (!matrix[characterRow][characterCol - 1].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow][characterCol - 1] = "X";
                    characterCol -= 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;
        }
            printMatrix(matrix);
        try {
            Thread.sleep(1000);
        }catch(Exception e){}
        movement(matrix, rand, characterRow, characterCol);
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
        Random rand = new Random();
        movement(matrix, rand, 1, 1);
    }
}
