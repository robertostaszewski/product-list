package com.product.productlist.services;

import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;

public interface SharingService {
    void shareListWith(ProductListId productListId, Username username);
}
