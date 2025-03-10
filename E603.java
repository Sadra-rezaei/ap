import java.util.Scanner;

public class E603 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        String[] list = str.split(" ");

        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length() ; j++) {

                char ch = list[i].charAt(j);
                if (Character.isUpperCase(ch)) {
                    System.out.println("Uppercase letter: " + ch);
                }
            }
        }
        System.out.println();

        for (int i = 0; i < list.length; i++) {
            System.out.println("Second character of every letter: " + list[i].charAt(1));
        }

        String str2 = "";
        int counter = 0;

        System.out.print("\npositions of all vowels in the string: ");
        for (int i = 0; i < str.length() ; i++) {
            char ch = str.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
                counter++;
                System.out.print(ch + ":" + i + "  ");
                ch = '_';
            }
            str2 += ch;
        }
        System.out.println("\n" + str2);
        System.out.println("\nnumber of vowels in the string: " + counter);

    }
}
