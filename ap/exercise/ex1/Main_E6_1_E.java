package ap.exercise.ex1;

import java.util.Scanner;

public class Main_E6_1_E {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0;

        do {
            if ((n % 10) % 2 != 0 ) {
                sum += n % 10;
            }
            n /= 10;
        } while (n > 0);
        System.out.println(sum);
    }
}
