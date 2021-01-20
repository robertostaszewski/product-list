package com.product.productlist.entity;

public class Product {
    private final ProductId productId;
    private final ProductListId productListId;
    private final String name;

    private Product(ProductId productId, ProductListId productListId, String name) {
        this.productId = productId;
        this.productListId = productListId;
        this.name = name;
    }

    public ProductListId getShoppingListId() {
        return productListId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public static Product of(ProductListId productListId, String name) {
        return new Product(ProductId.random(), productListId, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name != null ? name.equals(product.name) : product.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
