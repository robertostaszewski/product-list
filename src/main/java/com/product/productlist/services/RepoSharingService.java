package com.product.productlist.services;

import com.product.productlist.entity.ProductList;
import com.product.productlist.entity.ProductListId;
import com.product.productlist.entity.SharingEntry;
import com.product.productlist.entity.Username;
import com.product.productlist.repository.SharingEntryRepository;

import java.util.List;

public class RepoSharingService implements SharingService {
    private final SharingEntryRepository sharingEntryRepository;
    private final SharingEntryFactory sharingEntryFactory;

    public RepoSharingService(SharingEntryRepository sharingEntryRepository,
                              SharingEntryFactory sharingEntryFactory) {
        this.sharingEntryRepository = sharingEntryRepository;
        this.sharingEntryFactory = sharingEntryFactory;
    }

    @Override
    public SharingEntry newEntry(ProductList productList, Username username) {
        SharingEntry sharingEntry = sharingEntryFactory.newEntry(productList, username);

        if (sharingEntryRepository.isAlreadyShared(sharingEntry)) {
            throw new RuntimeException("List already shared " + sharingEntry.getProductListId().asString());
        }

        sharingEntryRepository.save(sharingEntry);
        return sharingEntry;
    }

    @Override
    public List<SharingEntry> getEntriesWith(ProductListId productListId) {
        return sharingEntryRepository.getAllForList(productListId);
    }

    @Override
    public List<SharingEntry> getSharedWith(Username username) {
        return sharingEntryRepository.getAllForUser(username);
    }

    @Override
    public void remove(SharingEntry sharingEntry) {
        sharingEntryRepository.remove(sharingEntry);
    }
}
