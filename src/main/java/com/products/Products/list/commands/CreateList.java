package com.products.Products.list.commands;

import com.products.Products.list.entity.ProductList;

public record CreateList(String name) implements Command<ProductList> {

    @Override
    public ProductList execute(CommandActions commandActions) {
        return commandActions.save(new ProductList(name));
    }
}
