package ap.exercises.ex2;

import java.util.Scanner;

public class EX2_PM_2_1 {
    public static void printMatrix(String[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void movement(String[][] matrix,Scanner sc, int characterRow, int characterCol){
        matrix[characterRow][characterCol] = "X";
        char way = sc.next().charAt(0);

        switch(way) {
            case 'w':
                System.out.println("UP");
                if (!matrix[characterRow - 1][characterCol].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow - 1][characterCol] = "X";
                    characterRow -= 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 'd':
                System.out.println("RIGHT");
                if (!matrix[characterRow][characterCol + 1].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow][characterCol + 1] = "X";
                    characterCol += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 's':
                System.out.println("DOWN");
                if (!matrix[characterRow + 1][characterCol].equals("*")) {
                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow + 1][characterCol] = "X";
                    characterRow += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 'a':
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
        movement(matrix, sc, characterRow, characterCol);
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
        movement(matrix, sc, 1, 1);
    }
}
