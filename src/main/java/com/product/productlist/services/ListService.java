package com.product.productlist.services;

import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;

public interface ListService {

    void createList(String name);

    void addProduct(ProductListId productListId, String productName);

    void removeProduct(ProductId productId);

    void removeList(ProductListId productListId);

}
