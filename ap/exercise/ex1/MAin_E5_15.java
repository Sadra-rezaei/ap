package ap.exercise.ex1;

import java.util.Scanner;

public class MAin_E5_15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number: ");
        float num = sc.nextFloat();

        if (num <= 50000) {
            System.out.println(num/100);
        }
        else if (num <= 75000) {
            System.out.println(num * 2 /100);
        }
        else if (num <= 100000) {
            System.out.println(num * 3 /100);
        }
        else if (num <= 250000) {
            System.out.println(num * 4 / 100);
        }
        else if (num <= 500000) {
            System.out.println(num * 5 / 100);
        }
        else {
            System.out.println(num * 6 / 100);
        }
    }
}
