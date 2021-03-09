package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.Username;

public interface ProductListFactory {
    ProductList newList(String name, Username owner);
}
