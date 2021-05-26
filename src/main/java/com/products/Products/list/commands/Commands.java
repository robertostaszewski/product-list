package com.products.Products.list.commands;

import com.products.Products.list.entity.ProductList;

public class Commands {

    public static AddProduct addProduct(ProductList productList, String productDescription) {
        return new AddProduct(productList, productDescription);
    }

    public static RemoveProduct removeProduct(Long listId, Long productId) {
        return new RemoveProduct(listId, productId);
    }

    public static GetList getList(Long listId) {
        return new GetList(listId);
    }

    public static CreateList createList(String listName) {
        return new CreateList(listName);
    }

    public static RemoveList removeList(Long listId) {
        return new RemoveList(listId);
    }

    public static GetAllLists getAllLists() {
        return new GetAllLists();
    }

    public static GetProductsForList getProductsForList(Long listId) {
        return new GetProductsForList(listId);
    }
}
