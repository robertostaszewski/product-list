package com.products.Products.list.commands;

import com.products.Products.list.entity.ProductList;

public record RemoveList(Long productListId) implements Command<ProductList> {

    @Override
    public ProductList execute(CommandActions commandActions) {
        ProductList productList = commandActions.getList(productListId).orElseThrow();
        commandActions.delete(productList);
        return productList;
    }
}
