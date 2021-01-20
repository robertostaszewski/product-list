package com.product.productlist.entity;

import java.util.UUID;

public class ProductListId extends Id {
    private ProductListId(String id) {
        super(id);
    }

    public static ProductListId from(String id) {
        return new ProductListId(id);
    }

    public static ProductListId random() {
        return new ProductListId(UUID.randomUUID().toString());
    }
}
