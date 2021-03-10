package com.product.productlist.repository;

import com.product.productlist.entity.*;

import java.util.List;

public interface SharingEntryRepository extends Repository<SharingEntry, SharingEntryId> {

    boolean isAlreadyShared(SharingEntry sharingEntry);

    List<SharingEntry> getAllForUser(Username username);

    List<SharingEntry> getAllForList(ProductListId productListId);
}
