package com.products.Products.list.service;

import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

public class ProductListService implements ListService {
    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;

    public ProductListService(ProductListRepository productListRepository, ProductRepository productRepository) {
        this.productListRepository = productListRepository;
        this.productRepository = productRepository;
    }

    @Override
    public <T> T execute(Command<T> command) {
        return command.execute(productListRepository, productRepository);
    }
}
