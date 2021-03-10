package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;

import java.util.List;

public interface ProductListService {

    ProductList newList(String name, Username owner);

    ProductList getList(ProductListId productListId);

    List<ProductList> getAllListsForUser(Username username);

    void remove(ProductList productList);
}
