package ap.exercises.trainingCodes.m2.d16;

public class Pen {
    private String color;
    private int price;
    private String brand;

    public Pen(String color, int price, String brand) {
        this.color = color;
        this.price = price;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "color='" + color + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
}
