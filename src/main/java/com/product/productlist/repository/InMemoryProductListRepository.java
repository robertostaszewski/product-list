package com.product.productlist.repository;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;

import java.util.*;

public class InMemoryProductListRepository implements ProductListRepository {
    private final Map<ProductListId, ProductList> shoppingListMap = new HashMap<>();

    @Override
    public void save(ProductList productList) {
        shoppingListMap.put(productList.getProductListId(), productList);
    }

    @Override
    public void update(ProductList productList) {
        if (shoppingListMap.containsKey(productList.getProductListId())) {
            shoppingListMap.replace(productList.getProductListId(), productList);
        }
    }

    @Override
    public Optional<ProductList> get(ProductListId id) {
        return shoppingListMap.containsKey(id) ? Optional.of(shoppingListMap.get(id)) : Optional.empty();
    }

    @Override
    public List<ProductList> getAll() {
        return new ArrayList<>(shoppingListMap.values());
    }

    @Override
    public void remove(ProductList productList) {
        shoppingListMap.remove(productList.getProductListId());
    }
}
