package ap.exercise1;

import java.util.ArrayList;
import java.util.Scanner;

public class E602 {
    public static void main(String[] args) {
        System.out.println("Enter some numbers: ");
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (sc.hasNextInt()) {
            int i = sc.nextInt();
            list.add(i);
        }

        findMinMax(list);
        findOddEven(list);
        findRepetition(list);

    }

    public static void findMinMax(ArrayList<Integer> list) {   // E6.2 a
        int min = list.get(0);
        int max = list.get(0);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < min) {
                min = list.get(i);
            }
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        System.out.println("Minimum: " + min);
        System.out.println("Maximum: " + max);
    }

    public static void findOddEven(ArrayList<Integer> list) {
        int evenCounter = 0;
        int oddCounter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                evenCounter++;
            } else {
                oddCounter++;
            }
        }

        System.out.println("Even numbers: " + evenCounter);
        System.out.println("Odd numbers: " + oddCounter);
    }

    public static void findRepetition(ArrayList<Integer> list) {
        list.sort(null);
        System.out.print("repetition: ");

        int temp = -1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals(list.get(i - 1))) {
                if (list.get(i) != temp) {
                    System.out.print(list.get(i) + " ");
                    temp = list.get(i);
                }
            }
        }
    }
}

