package com.product.productlist.repository;

import com.product.productlist.entity.Product;
import com.product.productlist.entity.ProductId;
import com.product.productlist.entity.ProductListId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<ProductId, Product> productMap = new HashMap<>();

    @Override
    public boolean productExists(Product product) {
        return productMap.values().stream()
                .anyMatch(p -> p.getName().equals(product.getName())
                        && p.getShoppingListId().equals(product.getShoppingListId()));
    }

    @Override
    public List<Product> getAllForList(ProductListId productListId) {
        return productMap.values().stream()
                .filter(product -> product.getShoppingListId().equals(productListId))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        productMap.put(product.getProductId(), product);
    }

    @Override
    public void update(Product product) {
        if (productMap.containsKey(product.getProductId())) {
            productMap.replace(product.getProductId(), product);
        }
    }

    @Override
    public Optional<Product> get(ProductId id) {
        return productMap.containsKey(id) ? Optional.of(productMap.get(id)) : Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public void remove(Product product) {
        productMap.remove(product.getProductId());
    }
}
