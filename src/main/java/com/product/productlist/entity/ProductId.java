package com.product.productlist.entity;

import java.util.UUID;

public class ProductId extends Id {
    private ProductId(String id) {
        super(id);
    }

    public static ProductId from(String id) {
        return new ProductId(id);
    }

    public static ProductId random() {
        return new ProductId(UUID.randomUUID().toString());
    }
}
