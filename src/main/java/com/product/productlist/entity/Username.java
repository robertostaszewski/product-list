package com.product.productlist.entity;

public class Username extends Id {
    protected Username(String id) {
        super(id);
    }

    public static Username from(String id) {
        return new Username(id);
    }
}
