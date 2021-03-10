package com.product.productlist.services;

import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;

public interface ListsService {

    void createList(String name, Username owner);

    void addProduct(ProductListId productListId, String productName);

    void removeProduct(ProductId productId);

    void removeList(ProductListId productListId);

    void shareListWith(ProductListId productListId, Username username);
}
