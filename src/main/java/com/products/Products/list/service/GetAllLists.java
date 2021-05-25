package com.products.Products.list.service;

import com.products.Products.list.entity.ProductList;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

import java.util.List;

public class GetAllLists implements Command<List<ProductList>>{

    @Override
    public List<ProductList> execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        return productListRepository.findAll();
    }
}
