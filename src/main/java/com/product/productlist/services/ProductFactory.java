package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductList;

public interface ProductFactory {
    Product newProduct(ProductList productList, String productName);
}
