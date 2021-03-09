package com.product.productlist.services;

import com.product.productlist.entity.*;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.ProductRepository;

public class ProductListService implements ListService {
    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;
    private final EventBus eventBus;
    private final ProductListFactory productListFactory;
    private final ProductFactory productFactory;

    public ProductListService(ProductListRepository productListRepository,
                              ProductRepository productRepository,
                              EventBus eventBus,
                              ProductListFactory productListFactory,
                              ProductFactory productFactory) {
        this.productListRepository = productListRepository;
        this.productRepository = productRepository;
        this.eventBus = eventBus;
        this.productListFactory = productListFactory;
        this.productFactory = productFactory;
    }

    @Override
    public void createList(String name, Username owner) {
        ProductList productList = productListFactory.newList(name, owner);
        productListRepository.save(productList);
        eventBus.listCreated(productList);
    }

    @Override
    public void addProduct(ProductListId productListId, String productName) {
        ProductList productList = productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("Shopping list " + productListId.asString() + " does not exists"));

        Product product = productFactory.newProduct(productList, productName);

        if (productRepository.productExists(product)) {
            throw new RuntimeException("Product already in the list " + productName);
        }

        productRepository.save(product);
        eventBus.productAdded(product);
    }

    @Override
    public void removeProduct(ProductId productId) {
        Product product = productRepository.get(productId)
                .orElseThrow(() -> new RuntimeException("Product not exists " + productId.asString()));

        productRepository.remove(product);
        eventBus.productRemoved(product);
    }

    @Override
    public void removeList(ProductListId productListId) {
        ProductList productList = productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("List not exists " + productListId.asString()));

        productRepository.getAllForList(productListId)
                .forEach(productRepository::remove);
        productListRepository.remove(productList);
        eventBus.listRemoved(productList);
    }

}
