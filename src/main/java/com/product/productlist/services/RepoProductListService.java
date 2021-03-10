package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.ProductListRepository;

import java.util.List;

public class RepoProductListService implements ProductListService {
    private final ProductListRepository productListRepository;
    private final ProductListFactory productListFactory;

    public RepoProductListService(ProductListRepository productListRepository, ProductListFactory productListFactory) {
        this.productListRepository = productListRepository;
        this.productListFactory = productListFactory;
    }

    @Override
    public ProductList newList(String name, Username owner) {
        ProductList productList = productListFactory.newList(name, owner);
        productListRepository.save(productList);
        return productList;
    }

    @Override
    public ProductList getList(ProductListId productListId) {
        return productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("Shopping list " + productListId.asString() + " does not exists"));
    }

    @Override
    public List<ProductList> getAllListsForUser(Username username) {
        return productListRepository.getAllForUser(username);
    }

    @Override
    public void remove(ProductList productList) {
        productListRepository.remove(productList);
    }
}
