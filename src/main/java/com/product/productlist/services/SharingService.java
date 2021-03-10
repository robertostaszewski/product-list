package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;

import java.util.List;

public interface SharingService {
    SharingEntry newEntry(ProductList productList, Username username);

    List<SharingEntry> getEntriesWith(ProductListId productListId);

    List<SharingEntry> getSharedWith(Username username);

    void remove(SharingEntry sharingEntry);
}
