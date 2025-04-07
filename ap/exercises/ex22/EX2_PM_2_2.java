package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class EX2_PM_2_2 {
    public static void printMatrix(String[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

//    public  static int calculateScore(String[][] matrix, int characterRow, int characterCol){}

    public static void movement(String[][] matrix, Scanner sc, int characterRow, int characterCol, int score){
        matrix[characterRow][characterCol] = "X";
        printMatrix(matrix);

        char way = sc.next().charAt(0);

        switch(way) {
            case 'w':
                System.out.println("UP");
                if (characterRow - 1 != 0) {
                    if (matrix[characterRow - 1][characterCol].equals("*"))
                        score ++;

                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow - 1][characterCol] = "X";
                    characterRow -= 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 'd':
                System.out.println("RIGHT");
                if (characterCol + 1 != matrix[0].length - 1) {
                    if (matrix[characterRow][characterCol + 1].equals("*"))
                        score ++;

                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow][characterCol + 1] = "X";
                    characterCol += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 's':
                System.out.println("DOWN");
                if (characterRow + 1 != matrix.length - 1) {
                    if (matrix[characterRow + 1][characterCol].equals("*"))
                        score ++;

                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow + 1][characterCol] = "X";
                    characterRow += 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;

            case 'a':
                System.out.println("LEFT");
                if (characterCol - 1 != 0) {
                    if (matrix[characterRow][characterCol - 1].equals("*"))
                        score ++;

                    matrix[characterRow][characterCol] = " ";
                    matrix[characterRow][characterCol - 1] = "X";
                    characterCol -= 1;
                } else
                    System.out.println("\nhitting the game wall\n");
                break;
        }
        System.out.println("Your score is " + score + "\n");
        try {
            Thread.sleep(1000);
        }catch(Exception e){}

        movement(matrix, sc, characterRow, characterCol, score);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int k = sc.nextInt();

        System.out.print("Enter the number of dots: ");
        int c = sc.nextInt();
        String[][] matrix = new String[k + 2][k + 2];
        int score = 0;


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

        movement(matrix, sc, 1, 1, score);
    }
}
