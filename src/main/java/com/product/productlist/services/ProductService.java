package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;

import java.util.List;

public interface ProductService {

    Product newProduct(String name, ProductList productList);

    Product get(ProductId productId);

    List<Product> getProducts(ProductListId productListId);

    void remove(Product product);
}
