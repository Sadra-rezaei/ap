package ap.exercises.ex2;

import java.util.Scanner;

public class EX2_PM_1_4 {
    public static void main(String[] args) {

        while (true) {
            char way;
            Scanner sc = new Scanner(System.in);
            way = sc.next().charAt(0);

            if (way == 'w' || way == 'W')
                System.out.println("UP");
            else if (way == 's' || way == 'S')
                System.out.println("DOWN");
            else if (way == 'd' || way == 'D')
                System.out.println("RIGHT");
            else if (way == 'a' || way == 'A')
                System.out.println("LEFT");
            else if (way == 'q' || way == 'Q') {
                System.out.println("EXIT");
                break;
            }
            else
                System.out.println("WARNING");
        }
    }
}
