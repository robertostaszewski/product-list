package com.product.productlist.presenters;

import com.product.productlist.entity.Username;
import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.entity.ProductListId;

import java.util.List;

public interface ListPresenter {
    ProductListDetails getListDetails(ProductListId productListId);

    List<ProductListDetails> getAllListsForUser(Username username);

    List<ProductDetails> getProductsForList(ProductListId productListId);
}
