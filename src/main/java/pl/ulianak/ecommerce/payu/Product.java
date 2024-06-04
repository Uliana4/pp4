package pl.ulianak.ecommerce.payu;

public class Product {
    String name;
    Integer unitPrice;

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Product setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    Integer quantity;
}
