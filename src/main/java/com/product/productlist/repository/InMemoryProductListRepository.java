package com.product.productlist.repository;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryProductListRepository extends InMemoryRepository<ProductList, ProductListId> implements ProductListRepository {

    public InMemoryProductListRepository() {
        super(ProductList::getProductListId);
    }

    @Override
    public List<ProductList> getAllForUser(Username username) {
        Predicate<ProductList> isOwner = productList -> productList.getOwner().equals(username);
        return getAll().stream()
                .filter(isOwner)
                .collect(Collectors.toList());
    }
}
