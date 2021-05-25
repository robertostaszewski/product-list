package com.products.Products.list.service;

import com.products.Products.list.entity.Product;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

import java.util.List;

public class GetProductsForList implements Command<List<Product>> {
    private final Long productListId;

    public GetProductsForList(Long productListId) {
        this.productListId = productListId;
    }

    @Override
    public List<Product> execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        return productRepository.findAllByProductListId(productListId);
    }
}
