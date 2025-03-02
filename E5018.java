import java.util.Arrays;
import java.util.Scanner;

public class E5018 {
    public static void main(String[] args) {
        System.out.println("Enter three strings:");
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        String str3 = sc.nextLine();

        String[] list = {str1, str2, str3};
        Arrays.sort(list);

        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }
}
