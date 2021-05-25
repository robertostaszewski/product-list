package com.products.Products.list.service;

import com.products.Products.list.entity.ProductList;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

public class RemoveList implements Command<ProductList> {
    private final Long productListId;

    public RemoveList(Long productListId) {
        this.productListId = productListId;
    }

    @Override
    public ProductList execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        ProductList productList = productListRepository.findById(productListId).orElseThrow();
        productListRepository.delete(productList);
        return productList;
    }
}
