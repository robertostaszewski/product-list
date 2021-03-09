package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;

public class SimpleSharingEntryFactory implements SharingEntryFactory {
    @Override
    public SharingEntry newEntry(ProductList productList, Username username) {
        return productList.shareWith(username);
    }
}
