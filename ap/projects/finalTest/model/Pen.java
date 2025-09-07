package ap.projects.finalTest.model;

import ap.projects.finalTest.Enums.Color;
import ap.projects.finalTest.Enums.ProductName;

public class Pen extends Product{
    private Color color;
    private String brand;

    public Pen(String name, Integer price) {
        super(name, price);
        super.productName = ProductName.Pen;
    }

    @Override
    public ProductName getType() {
        return super.getType();
    }
}
