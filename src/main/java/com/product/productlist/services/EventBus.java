package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductList;

public interface EventBus {
    void listCreated(ProductList productList);

    void listRemoved(ProductList productList);

    void productAdded(Product product);

    void productRemoved(Product product);
}
