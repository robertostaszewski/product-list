package com.product.productlist.entity;

public class SharingEntry {
    private final SharingEntryId sharingEntryId;
    private final ProductListId productListId;
    private final Username username;

    public SharingEntry(SharingEntryId sharingEntryId, ProductListId productListId, Username username) {
        this.sharingEntryId = sharingEntryId;
        this.productListId = productListId;
        this.username = username;
    }

    public SharingEntryId getSharingEntryId() {
        return sharingEntryId;
    }

    public ProductListId getProductListId() {
        return productListId;
    }

    public Username getUsername() {
        return username;
    }
}
