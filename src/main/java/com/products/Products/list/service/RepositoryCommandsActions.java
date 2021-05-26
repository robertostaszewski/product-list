package com.products.Products.list.service;

import com.products.Products.list.commands.CommandActions;
import com.products.Products.list.entity.Product;
import com.products.Products.list.entity.ProductList;
import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class RepositoryCommandsActions implements CommandActions {
    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;

    public RepositoryCommandsActions(ProductListRepository productListRepository, ProductRepository productRepository) {
        this.productListRepository = productListRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductList save(ProductList productList) {
        return productListRepository.save(productList);
    }

    @Override
    public Optional<ProductList> getList(Long listId) {
        return productListRepository.findById(listId);
    }

    @Override
    public void delete(ProductList productList) {
        productListRepository.delete(productList);
    }

    @Override
    public List<ProductList> getAllLists() {
        return productListRepository.findAll();
    }

    @Override
    public List<Product> getProductsForList(Long listId) {
        return productRepository.findAllByProductListId(listId);
    }
}
