package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.ProductRepository;

public class ProductListService implements ListService {
    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;
    private final EventBus eventBus;

    public ProductListService(ProductListRepository productListRepository,
                              ProductRepository productRepository,
                              EventBus eventBus) {
        this.productListRepository = productListRepository;
        this.productRepository = productRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void createList(String name) {
        ProductList productList = ProductList.of(name);
        productListRepository.save(productList);
        eventBus.listCreated(productList);
    }

    @Override
    public void addProduct(ProductListId productListId, String productName) {
        ProductList productList = productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("Shopping list " + productListId.getId() + " does not exists"));

        Product product = productList.addProduct(productName);

        if (productRepository.productExists(product)) {
            throw new RuntimeException("Product already in the list " + productName);
        }

        productRepository.save(product);
        eventBus.productAdded(product);
    }

    @Override
    public void removeProduct(ProductId productId) {
        Product product = productRepository.get(productId)
                .orElseThrow(() -> new RuntimeException("Product not exists " + productId.getId()));

        productRepository.remove(product);
        eventBus.productRemoved(product);
    }

    @Override
    public void removeList(ProductListId productListId) {
        ProductList productList = productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("List not exists " + productListId.getId()));

        productRepository.getAllForList(productListId)
                .forEach(productRepository::remove);
        productListRepository.remove(productList);
        eventBus.listRemoved(productList);
    }

}
