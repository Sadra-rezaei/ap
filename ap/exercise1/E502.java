package ap.exercise1;

import java.util.Scanner;

public class E502 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        float num = sc.nextFloat();

        if (num == 0) {
            System.out.println("0");
        }
        else if (num >= 0) {
            System.out.println("positive");
        }
        else {
            System.out.println("negative");
        }

        if (num >= -1 && num <= 1 && num != 0) {
            System.out.println("small");
        }
        else if (num >= 1000000 || num <= -1000000) {
            System.out.println("large");
        }
    }
}
