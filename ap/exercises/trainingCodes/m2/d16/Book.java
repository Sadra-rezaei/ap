package ap.exercises.trainingCodes.m2.d16;

public class Book {
    private String title;
    private int price;
//    private int discount = 1;

    public Book(String title, int price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

//    public int calculatePrice() {
//        return price;
//    }
//
//    public int calculatePrice(int discount) {
//        this.discount = discount;
//        return price * discount;
//    }


}
