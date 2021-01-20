package com.product.productlist.presenters;

import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListPresenter implements ListPresenter {
    private final ProductRepository productRepository;
    private final ProductListRepository productListRepository;

    public ProductListPresenter(ProductListRepository productListRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productListRepository = productListRepository;
    }

    @Override
    public ProductListDetails getListDetails(ProductListId productListId) {
        return productListRepository.get(productListId)
                .map(shoppingList -> new ProductListDetails(shoppingList.getProductListId().getId(), shoppingList.getName()))
                .orElseThrow(() -> new RuntimeException("List does not exists"));
    }

    @Override
    public List<ProductListDetails> getAllLists() {
        return productListRepository.getAll().stream()
                .map(shoppingList -> new ProductListDetails(shoppingList.getProductListId().getId(), shoppingList.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetails> getProductsForList(ProductListId productListId) {
        return productRepository.getAllForList(productListId).stream()
                .map(product -> new ProductDetails(product.getShoppingListId().getId(), product.getProductId().getId(), product.getName()))
                .collect(Collectors.toList());
    }
}
