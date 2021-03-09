package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.ProductListRepository;
import com.product.productlist.repository.SharingEntryRepository;

public class ProductListSharingService implements SharingService {
    private final ProductListRepository productListRepository;
    private final SharingEntryFactory sharingEntryFactory;
    private final SharingEntryRepository sharingEntryRepository;

    public ProductListSharingService(ProductListRepository productListRepository,
                                     SharingEntryFactory sharingEntryFactory,
                                     SharingEntryRepository sharingEntryRepository) {
        this.productListRepository = productListRepository;
        this.sharingEntryFactory = sharingEntryFactory;
        this.sharingEntryRepository = sharingEntryRepository;
    }

    @Override
    public void shareListWith(ProductListId productListId, Username username) {
        ProductList productList = productListRepository.get(productListId)
                .orElseThrow(() -> new RuntimeException("List not exists " + productListId.asString()));

        SharingEntry sharingEntry = sharingEntryFactory.newEntry(productList, username);

        if (sharingEntryRepository.isAlreadyShared(sharingEntry)) {
            throw new RuntimeException("List already shared " + sharingEntry.getProductListId().asString());
        }

        sharingEntryRepository.save(sharingEntry);
    }
}
