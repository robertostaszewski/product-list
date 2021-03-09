package com.product.productlist.repository;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository extends InMemoryRepository<Product, ProductId> implements ProductRepository {

    public InMemoryProductRepository() {
        super(Product::getProductId);
    }

    @Override
    public boolean productExists(Product product) {
        return getAll().stream()
                .anyMatch(p -> p.getName().equals(product.getName())
                        && p.getProductListId().equals(product.getProductListId()));
    }

    @Override
    public List<Product> getAllForList(ProductListId productListId) {
        return getAll().stream()
                .filter(product -> product.getProductListId().equals(productListId))
                .collect(Collectors.toList());
    }
}
