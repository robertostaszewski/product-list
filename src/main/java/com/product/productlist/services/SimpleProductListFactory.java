package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.Username;

public class SimpleProductListFactory implements ProductListFactory {
    @Override
    public ProductList newList(String name, Username owner) {
        return ProductList.of(name, owner);
    }
}
