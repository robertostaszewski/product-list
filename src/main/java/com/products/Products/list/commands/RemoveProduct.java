package com.products.Products.list.commands;

import com.products.Products.list.entity.Product;

public record RemoveProduct(Long listId, Long productId) implements Command<Product> {

    @Override
    public Product execute(CommandActions commandActions) {
        Product product = commandActions.getProduct(productId).orElseThrow();

        if (!product.getProductList().getId().equals(listId)) {
            throw new RuntimeException("Product is not in given list");
        }

        commandActions.delete(product);
        return product;
    }
}
