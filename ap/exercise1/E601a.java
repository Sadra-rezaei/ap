package ap.exercise1;

public class E601a {
    public static void main(String[] args) {
        short sum = 0;
        for (short i = 4; i < 100; i+=2) {
            sum += i;
        }
        System.out.println(sum);
    }
}
