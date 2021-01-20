package com.product.productlist.entity;

public class ProductList {
    private final ProductListId productListId;
    private final String name;

    private ProductList(ProductListId productListId, String name) {
        this.productListId = productListId;
        this.name = name;
    }

    public ProductListId getProductListId() {
        return productListId;
    }

    public String getName() {
        return name;
    }

    public Product addProduct(String productName) {
        return Product.of(productListId, productName);
    }

    public static ProductList of(String name) {
        return new ProductList(ProductListId.random(), name);
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "name='" + name + '\'' +
                '}';
    }
}
