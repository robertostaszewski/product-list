package com.product.productlist.repository;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;

import java.util.List;

public interface ProductRepository extends Repository<Product, ProductId> {

    boolean productExists(Product product);

    List<Product> getAllForList(ProductListId productListId);
}
