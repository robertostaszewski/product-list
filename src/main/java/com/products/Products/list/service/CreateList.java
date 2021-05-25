package com.products.Products.list.service;

import com.products.Products.list.entity.ProductList;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

public class CreateList implements Command<ProductList> {

    private final String name;

    public CreateList(String name) {
        this.name = name;
    }

    @Override
    public ProductList execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        return productListRepository.save(new ProductList(name));
    }
}
