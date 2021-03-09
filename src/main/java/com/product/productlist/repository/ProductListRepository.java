package com.product.productlist.repository;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.Username;

import java.util.List;

public interface ProductListRepository extends Repository<ProductList, ProductListId> {

    List<ProductList> getAllForUser(Username username);
}
