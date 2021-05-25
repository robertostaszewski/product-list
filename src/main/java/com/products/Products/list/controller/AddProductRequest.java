package com.products.Products.list.controller;

public class AddProductRequest {
    private String productDescription;

    public AddProductRequest() {
    }

    public AddProductRequest(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
