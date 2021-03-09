package com.product.productlist.entity;

import java.util.UUID;

public class SharingEntryId extends Id {
    protected SharingEntryId(String id) {
        super(id);
    }

    public static SharingEntryId random() {
        return new SharingEntryId(UUID.randomUUID().toString());
    }
}
