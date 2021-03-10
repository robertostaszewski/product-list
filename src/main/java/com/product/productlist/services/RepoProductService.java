package com.product.productlist.services;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.repository.ProductRepository;

import java.util.List;

public class RepoProductService implements ProductService {
    private final ProductRepository productRepository;
    private final ProductFactory productFactory;

    public RepoProductService(ProductRepository productRepository, ProductFactory productFactory) {
        this.productRepository = productRepository;
        this.productFactory = productFactory;
    }

    @Override
    public Product newProduct(String productName, ProductList productList) {
        Product product = productFactory.newProduct(productName, productList);

        if (productRepository.productExists(product)) {
            throw new RuntimeException("Product already in the list " + productName);
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public Product get(ProductId productId) {
        return productRepository.get(productId)
                .orElseThrow(() -> new RuntimeException("Product not exists " + productId.asString()));
    }

    @Override
    public List<Product> getProducts(ProductListId productListId) {
        return productRepository.getAllForList(productListId);
    }

    @Override
    public void remove(Product product) {
        productRepository.remove(product);
    }
}
