package com.products.Products.list.commands;

import com.products.Products.list.entity.Product;
import com.products.Products.list.entity.ProductList;

public record AddProduct(ProductList productList, String productDescription) implements Command<Product> {

    @Override
    public Product execute(CommandActions commandActions) {
        return commandActions.save(new Product(productDescription, productList));
    }
}
