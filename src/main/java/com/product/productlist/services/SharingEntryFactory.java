package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;

public interface SharingEntryFactory {

    SharingEntry newEntry(ProductList productList, Username username);
}
