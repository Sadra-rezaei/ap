package ap.projects.finalTest.model;

import ap.projects.finalTest.Enums.ProductName;

import java.util.Objects;

public class Product{
    private String name;
    private Integer price;
    protected ProductName productName;

    public Product(String name, Integer price){
        this.name = name;
        this.price = price;
        this.productName = ProductName.Null;
    }

    public Integer getPrice() {
        return price;
    }

    public int compareTo(Product p){
        return Integer.compare(this.price,p.price);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public String toString() {
        return "name: " + this.name + ", price: " + this.price;
    }

    public ProductName getType() {
        return productName;
    }
}
