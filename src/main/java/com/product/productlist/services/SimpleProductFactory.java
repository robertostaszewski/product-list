package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductList;

public class SimpleProductFactory implements ProductFactory {
    @Override
    public Product newProduct(ProductList productList, String productName) {
        return productList.addProduct(productName);
    }
}
