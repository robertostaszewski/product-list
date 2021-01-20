package com.product.productlist.presenters.dto;

public class ProductDetails {
    private final String listId;
    private final String productId;
    private final String name;

    public ProductDetails(String listId, String productId, String name) {
        this.listId = listId;
        this.productId = productId;
        this.name = name;
    }

    public String getListId() {
        return listId;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }
}
