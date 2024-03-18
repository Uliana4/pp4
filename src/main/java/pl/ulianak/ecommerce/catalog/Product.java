package pl.ulianak.ecommerce.catalog;

import java.util.UUID;

public class Product {
    private final String id;
    private final String name;
    private final String description;

    public Product(UUID id, String name, String description) {

        this.id = id.toString();
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return null;
    }
}
