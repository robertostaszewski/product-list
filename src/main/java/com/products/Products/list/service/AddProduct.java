package com.products.Products.list.service;

import com.products.Products.list.entity.Product;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

public class AddProduct implements Command<Product>{
    private final Long productListId;
    private final String productDescription;

    public AddProduct(Long productListId, String productDescription) {
        this.productListId = productListId;
        this.productDescription = productDescription;
    }

    @Override
    public Product execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        return productListRepository.findById(productListId)
                .map(productList -> productRepository.save(new Product(productDescription, productList)))
                .orElseThrow();
    }
}
