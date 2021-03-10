package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.SharingEntry;

public interface EventBus {
    void listCreated(ProductList productList);

    void listRemoved(ProductList productList);

    void productAdded(Product product);

    void productRemoved(Product product);

    void listShared(SharingEntry entry);
}
