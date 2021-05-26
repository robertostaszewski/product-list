package com.products.Products.list.commands;

import com.products.Products.list.entity.Product;
import com.products.Products.list.entity.ProductList;

import java.util.List;
import java.util.Optional;

public interface CommandActions {

    Product save(Product product);

    void delete(Product product);

    Optional<Product> getProduct(Long productId);

    ProductList save(ProductList productList);

    Optional<ProductList> getList(Long listId);

    void delete(ProductList productList);

    List<ProductList> getAllLists();

    List<Product> getProductsForList(Long listId);
}
