package com.products.Products.list.commands;

import com.products.Products.list.entity.Product;

import java.util.List;

public record GetProductsForList(Long productListId) implements Command<List<Product>> {

    @Override
    public List<Product> execute(CommandActions commandActions) {
        return commandActions.getProductsForList(productListId);
    }
}
