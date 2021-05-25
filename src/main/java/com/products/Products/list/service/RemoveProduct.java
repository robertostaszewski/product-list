package com.products.Products.list.service;

import com.products.Products.list.entity.Product;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

public class RemoveProduct implements Command<Product> {

    private final Long productId;

    public RemoveProduct(Long productId) {
        this.productId = productId;
    }

    @Override
    public Product execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        Product product = productRepository.findById(productId).orElseThrow();
        productRepository.delete(product);
        return product;
    }
}
