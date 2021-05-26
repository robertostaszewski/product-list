package com.products.Products.list.commands;

import com.products.Products.list.entity.ProductList;

public record GetList(Long listId) implements Command<ProductList> {

    @Override
    public ProductList execute(CommandActions commandActions) {
        return commandActions.getList(listId).orElseThrow();
    }
}
