package ap.exercise.ex1;

public class Main_E6_1_A {
    public static void main(String[] args) {
        short sum = 0;
        for (short i = 4; i < 100; i+=2) {
            sum += i;
        }
        System.out.println(sum);
    }
}
