package ap.projects.finalTest.method;

import ap.projects.finalTest.Enums.ProductName;
import ap.projects.finalTest.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductTools {

    public void showProduct(ArrayList<Product> products){
        ArrayList<Product> sortedProducts = new ArrayList<>();

        for (Product product : products) {
            if (!sortedProducts.contains(product)) {
                sortedProducts.add(product);
            }
        }
        sortedProducts.sort(Comparator.comparing(Product::getPrice));
        sortedProducts.forEach(System.out::println);
    }

    public String getMaxProduct(ArrayList<Product> products){

        Product maxBook = null;
        for (Product product : products) {
            if (product.getType().equals(ProductName.Book)) {
                if(maxBook == null){
                    maxBook = product;
                } else if (product.getPrice() > maxBook.getPrice()) {
                    maxBook = product;
                }
            }
        }

        Product maxPen = null;
        for (Product product : products) {
            if (product.getType().equals(ProductName.Pen)) {
                if(maxPen == null){
                    maxPen = product;
                } else if (product.getPrice() > maxPen.getPrice()) {
                    maxPen = product;
                }
            }
        }
        return "max pen price:"+ maxPen.getPrice() + "\nmax book price:"+ maxBook.getPrice() ;
    }
}
