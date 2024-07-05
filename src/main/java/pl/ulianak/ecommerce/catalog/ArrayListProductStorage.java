package pl.ulianak.ecommerce.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListProductStorage implements ProductStorage {
    private ArrayList<Product> products;

    public ArrayListProductStorage(){
        this.products = new ArrayList<>();
    }

    @Override
    public List<Product> getAllProducts(){
        return Collections.unmodifiableList(products);
    }

    @Override
    public void add(Product newProduct)
    {
        products.add(newProduct);
    }

    @Override
    public Product getProductById(String id) {
        return products.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public void setUpDatabase() {

    }
}