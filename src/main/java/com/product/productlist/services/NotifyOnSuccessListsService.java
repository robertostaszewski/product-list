package com.product.productlist.services;

import com.product.productlist.entity.*;

public class NotifyOnSuccessListsService implements ListsService {
    private final ProductListService productListService;
    private final ProductService productService;
    private final SharingService sharingService;
    private final EventBus eventBus;

    public NotifyOnSuccessListsService(ProductListService productListService,
                                       ProductService productService,
                                       SharingService sharingService,
                                       EventBus eventBus) {
        this.productListService = productListService;
        this.productService = productService;
        this.sharingService = sharingService;
        this.eventBus = eventBus;
    }

    @Override
    public void createList(String name, Username owner) {
        ProductList productList = productListService.newList(name, owner);
        eventBus.listCreated(productList);
    }

    @Override
    public void addProduct(ProductListId productListId, String productName) {
        ProductList productList = productListService.getList(productListId);
        Product product = productService.newProduct(productName, productList);
        eventBus.productAdded(product);
    }

    @Override
    public void removeProduct(ProductId productId) {
        Product product = productService.get(productId);
        productService.remove(product);
        eventBus.productRemoved(product);
    }

    @Override
    public void removeList(ProductListId productListId) {
        ProductList productList = productListService.getList(productListId);

        productService.getProducts(productListId)
                .forEach(productService::remove);
        sharingService.getEntriesWith(productListId)
                .forEach(sharingService::remove);
        productListService.remove(productList);
        eventBus.listRemoved(productList);
    }

    @Override
    public void shareListWith(ProductListId productListId, Username username) {
        ProductList productList = productListService.getList(productListId);
        SharingEntry sharingEntry = sharingService.newEntry(productList, username);
        eventBus.listShared(sharingEntry);
    }

}
