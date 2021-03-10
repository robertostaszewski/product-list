package com.product.productlist.presenters;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.Username;
import com.product.productlist.presenters.dto.ProductDetails;
import com.product.productlist.presenters.dto.ProductListDetails;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.services.ProductListService;
import com.product.productlist.services.ProductService;
import com.product.productlist.services.SharingService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListPresenter implements ListPresenter {
    private final ProductListService productListService;
    private final ProductService productService;
    private final SharingService sharingService;

    public ProductListPresenter(ProductListService productListService,
                                ProductService productService,
                                SharingService sharingService) {
        this.productListService = productListService;
        this.productService = productService;
        this.sharingService = sharingService;
    }

    @Override
    public ProductListDetails getListDetails(ProductListId productListId) {
        ProductList productList = productListService.getList(productListId);
        return new ProductListDetails(productList.getProductListId().asString(), productList.getName());
    }

    @Override
    public List<ProductListDetails> getAllListsFor(Username username) {
        return productListService.getAllListsForUser(username).stream()
                .map(productList -> new ProductListDetails(productList.getProductListId().asString(), productList.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductListDetails> getAllListsSharedWith(Username username) {
        return sharingService.getSharedWith(username).stream()
                .map(entry -> productListService.getList(entry.getProductListId()))
                .map(productList -> new ProductListDetails(productList.getProductListId().asString(), productList.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetails> getProductsForList(ProductListId productListId) {
        return productService.getProducts(productListId).stream()
                .map(product -> new ProductDetails(product.getProductListId().asString(), product.getProductId().asString(), product.getName()))
                .collect(Collectors.toList());
    }
}
