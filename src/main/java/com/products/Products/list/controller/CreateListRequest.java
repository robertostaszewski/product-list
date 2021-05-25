package com.products.Products.list.controller;

public class CreateListRequest {
    private String listName;

    public CreateListRequest() {
    }

    public CreateListRequest(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
