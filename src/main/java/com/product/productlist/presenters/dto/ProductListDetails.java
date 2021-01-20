package com.product.productlist.presenters.dto;

public class ProductListDetails {
    private final String id;
    private final String listName;

    public ProductListDetails(String id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    public String getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }
}
